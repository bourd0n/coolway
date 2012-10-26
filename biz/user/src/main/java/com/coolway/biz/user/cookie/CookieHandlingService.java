package com.coolway.biz.user.cookie;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.codec.Base64;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.coolway.dao.UserMapper;
import com.coolway.entity.User;
import com.coolway.entity.UserExample;

@Service
public class CookieHandlingService {

    private static final Logger logger                     = LoggerFactory.getLogger(CookieHandlingService.class);

    // private static JsonBinder jsonBinder = JsonBinder.buildNormalBinder();

    public static final String  DEFAULT_COOKIE_NAME        = "oau";

    public static final String  DEFAULT_AdminT_COOKIE_NAME = "aoau";

    @Autowired
    private UserMapper          userMapper;

    // @Autowired
    // private AdminTMapper admintMapper;
    //
    // @Autowired
    // private CheckinService checkinService;
    //
    // @Autowired
    // private MessageService messageService;
    //
    // @Autowired
    // private SnsDataService snsDataService;
    //
    // @Autowired
    // private SnsRegisterStatisticService snsRegisterStatisticService;

    // @Autowired
    // private ThreadPoolService threadPoolService;

    private SecureRandom        random                     = null;

    public static final int     DEFAULT_SERIES_LENGTH      = 16;
    public static final int     DEFAULT_TOKEN_LENGTH       = 16;

    private int                 seriesLength               = DEFAULT_SERIES_LENGTH;
    private int                 tokenLength                = DEFAULT_TOKEN_LENGTH;
    private static final String DELIMITER                  = ":";
    private String              cookieName                 = DEFAULT_COOKIE_NAME;
    private String              adminCookieName            = DEFAULT_AdminT_COOKIE_NAME;
    private boolean             useSecureCookie            = false;

    /** 保存在浏览器中cookie的最大时间14天 */
    public final static int     BCOOKIE_MAX_AGE            = 14 * 24 * 60 * 60;

    /** 保存在浏览器中cookie的最大时间14天 */
    public final static int     AdminT_MAX_AGE             = 14 * 24 * 60 * 60;

    public CookieHandlingService(){
        super();
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // by kenny //@Transactional
    public void doCookieLogin(User userinfo, HttpServletRequest request, HttpServletResponse response) {
        String series = generateSeriesData();
        // userinfo.setLoginSeries(series);
        setCookie(new String[] { userinfo.getId().toString(), series }, BCOOKIE_MAX_AGE, cookieName, request, response);
        userMapper.updateByPrimaryKeySelective(userinfo);
    }

    public User autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String rememberMeCookie = getCookie(cookieName, request);

        if (rememberMeCookie == null) {
            return null;
        }
        logger.debug("Remember-me cookie detected");
        if (rememberMeCookie.length() == 0) {
            logger.debug("Cookie was empty");
            cancelCookie(cookieName, request, response);
            return null;
        }

        User user = null;

        String[] cookieTokens = decodeCookie(rememberMeCookie);
        if (cookieTokens != null && cookieTokens.length > 1) {
            Long userId = Long.parseLong(cookieTokens[0]);
            UserExample example = new UserExample();
            // example.createCriteria().andIdEqualTo(userId).andLoginSeriesEqualTo(cookieTokens[1]);
            List<User> users = userMapper.selectByExample(example);// .verifyLoginSeries(map);
            if (users == null || users.size() < 1) {
                cancelCookie(cookieName, request, response);
            } else {
                user = users.get(0);
                // 用户登录时,系统生成个人消息
                // messageService.sendMessageEvent(user.getTuser());//TODO 修改 *
                // 从SNS社区读取用户好友信息
                // snsDataService.getSnsFriends(user.getTuser(), null);//TODO 修改
                // *

            }
        }
        return user;
    }

    public void logout(User userinfo, HttpServletRequest request, HttpServletResponse response) {
        if (userinfo == null) return;
        cancelCookie(this.cookieName, request, response);
        // userinfo.setLoginSeries(null);
        userMapper.updateByPrimaryKeySelective(userinfo);
        // userInfoMapper.updateUser(userinfo);
    }

    // by kenny //@Transactional
    // public void doCookieLogin(AdminT admin, HttpServletRequest request, HttpServletResponse response) {
    // String series = generateSeriesData();
    // if ("robot".equals(admin.getUsername())) {
    // series = admin.getLoginSeries();
    // }
    // admin.setLoginSeries(series);
    // setCookie(new String[] { admin.getId().toString(), series }, AdminT_MAX_AGE, adminCookieName, request, response);
    // //admintMapper.updateByPrimaryKeySelective(admin);
    // }

    // @Override
    // public AdminT loginForRobot(String token) {
    // AdminT admin = null;
    // admin = ADMIN_CACHE.get(token);
    // if (admin != null) return admin;
    // String[] cookieTokens = decodeCookie(token);
    // AdminTExample example = new AdminTExample();
    // example.createCriteria().andIdEqualTo(Long.valueOf(cookieTokens[0])).andLoginSeriesEqualTo(cookieTokens[1]);
    // List<AdminT> list = admintMapper.selectByExample(example);
    // if (list != null && list.size() > 0) {
    // admin = list.get(0);
    // }
    // /*
    // * Map<String, String> map = new HashMap<String, String>(); map.put("id", cookieTokens[0]);
    // * map.put("loginSeries", cookieTokens[1]); admin = admintMapper.verifyLoginSeries(map);
    // */
    // if (admin != null) {
    // ADMIN_CACHE.put(token, admin);
    // }
    // return admin;
    // }

    /**
     * 通过Filter 自动登录
     * 
     * @see com.guang.prada.service.common.website.user.service.CookieHandlingService#autoLogin(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     * @since Guang 0.0.1
     */
    // public AdminT autoLoginForAdmin(HttpServletRequest request, HttpServletResponse response) {
    //
    // String rememberMeCookie = getCookie(adminCookieName, request);
    //
    // if (rememberMeCookie == null) {
    // return null;
    // }
    //
    // logger.debug("Remember-me cookie detected");
    //
    // if (rememberMeCookie.length() == 0) {
    // logger.debug("Cookie was empty");
    // cancelCookie(adminCookieName, request, response);
    // return null;
    // }
    //
    // AdminT admin = null;
    //
    // String[] cookieTokens = decodeCookie(rememberMeCookie);
    // /*
    // * Map<String, String> map = new HashMap<String, String>(); map.put("id", cookieTokens[0]);
    // * map.put("loginSeries", cookieTokens[1]); admin = AdminTDao.verifyLoginSeries(map); if (admin == null) {
    // * cancelCookie(AdminTCookieName, request, response); }
    // */
    // Long adminId = Long.parseLong(cookieTokens[0]);
    // AdminTExample example = new AdminTExample();
    // example.createCriteria().andIdEqualTo(adminId).andLoginSeriesEqualTo(cookieTokens[1]);
    // List<AdminT> admins = admintMapper.selectByExample(example);
    // if (admins == null || admins.size() < 1) {
    // cancelCookie(adminCookieName, request, response);
    // } else {
    // admin = admins.get(0);
    // }
    // return admin;
    // }

    // public void logout(AdminT admin, HttpServletRequest request, HttpServletResponse response) {
    // if (admin == null) return;
    // cancelCookie(this.adminCookieName, request, response);
    // if (!"robot".equals(admin.getUsername())) {
    // admin.setLoginSeries(null);
    // }
    // this.admintMapper.updateByPrimaryKeySelective(admin);
    // }

    protected String generateSeriesData() {
        byte[] newSeries = new byte[seriesLength];
        random.nextBytes(newSeries);
        return new String(Base64.encode(newSeries));
    }

    protected String generateTokenData() {
        byte[] newToken = new byte[tokenLength];
        random.nextBytes(newToken);
        return new String(Base64.encode(newToken));
    }

    /**
     * Decodes the cookie and splits it into a set of token strings using the ":" delimiter.
     * 
     * @param cookieValue the value obtained from the submitted cookie
     * @return the array of tokens.
     * @throws InvalidCookieException if the cookie was not base64 encoded.
     */
    public String[] decodeCookie(String cookieValue) throws InvalidCookieException {
        for (int j = 0; j < cookieValue.length() % 4; j++) {
            cookieValue = cookieValue + "=";
        }

        if (!Base64.isBase64(cookieValue.getBytes())) {
            throw new InvalidCookieException("Cookie token was not Base64 encoded; value was '" + cookieValue + "'");
        }

        String cookieAsPlainText = new String(Base64.decode(cookieValue.getBytes()));

        String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, DELIMITER);

        if ((tokens[0].equalsIgnoreCase("http") || tokens[0].equalsIgnoreCase("https")) && tokens[1].startsWith("//")) {
            // Assume we've accidentally split a URL (OpenID identifier)
            String[] newTokens = new String[tokens.length - 1];
            newTokens[0] = tokens[0] + ":" + tokens[1];
            System.arraycopy(tokens, 2, newTokens, 1, newTokens.length - 1);
            tokens = newTokens;
        }

        return tokens;
    }

    /**
     * Inverse operation of decodeCookie.
     * 
     * @param cookieTokens the tokens to be encoded.
     * @return base64 encoding of the tokens concatenated with the ":" delimiter.
     */
    public String encodeCookie(String[] cookieTokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookieTokens.length; i++) {
            sb.append(cookieTokens[i]);

            if (i < cookieTokens.length - 1) {
                sb.append(DELIMITER);
            }
        }

        String value = sb.toString();

        sb = new StringBuilder(new String(Base64.encode(value.getBytes())));

        while (sb.charAt(sb.length() - 1) == '=') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**
     * Sets a "cancel cookie" (with maxAge = 0) on the response to disable persistent logins.
     * 
     * @param request
     * @param response
     */
    public void cancelCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Cancelling cookie");
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));

        response.addCookie(cookie);
    }

    /**
     * Sets the cookie on the response
     * 
     * @param tokens the tokens which will be encoded to make the cookie value.
     * @param maxAge the value passed to {@link Cookie#setMaxAge(int)}
     * @param cookieName
     * @param request the request
     * @param response the response to add the cookie to.
     */
    public void setCookie(String[] tokens, int maxAge, String cookieName, HttpServletRequest request,
                          HttpServletResponse response) {
        String cookieValue = encodeCookie(tokens);
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getCookiePath(request));
        cookie.setSecure(useSecureCookie);
        response.addCookie(cookie);
    }

    public String[] getAndDecodeCookie(String cookieName, HttpServletRequest request) {

        String cookieValue = getCookie(cookieName, request);
        if (cookieValue != null) {
            return decodeCookie(cookieValue);
        }
        return null;
    }

    public String getCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            if (cookieName.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

    public void setCookieName(String cookieName) {
        Assert.hasLength(cookieName, "Cookie name cannot be empty or null");
        this.cookieName = cookieName;
    }

}

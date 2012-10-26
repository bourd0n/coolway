//package com.coolway.common.utils.render;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.oro.text.regex.MalformedPatternException;
//import org.apache.oro.text.regex.MatchResult;
//import org.apache.oro.text.regex.Pattern;
//import org.apache.oro.text.regex.PatternCompiler;
//import org.apache.oro.text.regex.PatternMatcher;
//import org.apache.oro.text.regex.PatternMatcherInput;
//import org.apache.oro.text.regex.Perl5Compiler;
//import org.apache.oro.text.regex.Perl5Matcher;
//import org.apache.oro.text.regex.Util;
//
///**
// * 处理含有@功能的文本：
// * 1. 过滤出文本中的@后的所有内容
// * 2. 过滤出文本开头的被回复者
// * 3. 提供替换内容中所有@xxx的方法
// * 
// * @author zhaopeng.xuzp May 15, 2012 6:04:44 PM
// */
//public class AtRenderUtils {
//
//    private static Pattern allAtPattern   = null;
//    private static Pattern replyedPattern = null;
//    // private static String regex = "@\\w*(\\s*|:)";@\d{1,20}"
//    // private static String regex = "@\\w{1,100}(\\s+|:+|：+|@)";
//    // private static String regex1 = "@\\w{1,100}";
//    // private static String regex3 = "@(\\S{1,100}"; // 用户名最大长度是100？
//    // private static String regex4 = "@([-+.]\\w+)*";
//    
//    private static String  regex          = "@[^@^\\s^。^，^：^；^！^？^\\.^\\,^:^;^!^\\?^#]+";      // 以@开头以.,:等结束的字符串
//    private static String  regex2         = "^回复\\s@[^@^\\s^。^，^：^；^！^？^\\.^\\,^:^;^!^\\?^#]+"; // 要得到被回复的人
//
//    static {
//        try {
//            PatternCompiler compiler = new Perl5Compiler();
//            allAtPattern = compiler.compile(regex, Perl5Compiler.READ_ONLY_MASK | Perl5Compiler.CASE_INSENSITIVE_MASK
//                                                   | Perl5Compiler.MULTILINE_MASK);
//            replyedPattern = compiler.compile(regex2, Perl5Compiler.READ_ONLY_MASK
//                                                      | Perl5Compiler.CASE_INSENSITIVE_MASK
//                                                      | Perl5Compiler.MULTILINE_MASK);
//
//        } catch (MalformedPatternException e) {
//            throw new RuntimeException("pattern compile error ! ", e);
//        }
//    }
//
//    public static Set<String> fetchAtUserNames(String content) {
//        Set<String> matchedText = new HashSet<String>();
//        if (StringUtils.isBlank(content)) {
//            return matchedText;
//        }
//        PatternMatcher matcher = new Perl5Matcher();
//        PatternMatcherInput input = new PatternMatcherInput(content);
//        MatchResult result = null;
//
//        while (matcher.contains(input, allAtPattern)) {
//            result = matcher.getMatch();
//            String matchs = StringUtils.substring(result.toString(), 1).trim();
//            matchedText.add(matchs);
//        }
//        return matchedText;
//    }
//
//    /**
//     * 取所有匹配的部分，按匹配的offset排序
//     */
//    public static List<MatchResult> fetchAtMatches(String content) {
//        List<MatchResult> matched = new ArrayList<MatchResult>();
//        if (StringUtils.isBlank(content)) {
//            return matched;
//        }
//        PatternMatcher matcher = new Perl5Matcher();
//        PatternMatcherInput input = new PatternMatcherInput(content);
//        MatchResult result = null;
//
//        while (matcher.contains(input, allAtPattern)) {
//            result = matcher.getMatch();
//            matched.add(result);
//        }
//        return matched;
//    }
//    
//    public static String fetchReplyedUser(String content) {
//        String matchedText = "";
//        if (StringUtils.isBlank(content)) {
//            return matchedText;
//        }
//        PatternMatcher matcher = new Perl5Matcher();
//        PatternMatcherInput input = new PatternMatcherInput(content);
//        MatchResult result = null;
//        while (matcher.contains(input, replyedPattern)) {// .contains(input, pattern)) {
//            result = matcher.getMatch();
//            matchedText = StringUtils.substring(result.toString(), 4).trim();
//            break;
//        }
//        return matchedText;
//    }
//
//    /**
//     * 将内容中的@userName 用map中的<userName,templete>替换掉
//     * 
//     * @param content
//     * @param replacement
//     * @return
//     */
//    public static String substituteAtUserName(String content, Map<String, String> replacement) {
//        PatternMatcher matcher = new Perl5Matcher();
//        return substitute(matcher, allAtPattern, content, replacement);
//    }
//
//    private static String substitute(PatternMatcher matcher, Pattern pattern, String input, Map<String, String> params) {
//        StringBuffer buffer = new StringBuffer(input.length());
//        PatternMatcherInput pinput = new PatternMatcherInput(input);
//        if (substitute(buffer, matcher, pattern, pinput, params, Util.SUBSTITUTE_ALL) != 0) return buffer.toString();
//        return input;
//    }
//
//    private static int substitute(StringBuffer result, PatternMatcher matcher, Pattern pattern,
//                                  PatternMatcherInput input, Map<String, String> params, int numSubs) { // numsubs=-1
//                                                                                                        // all sub
//        int beginOffset, subCount;
//        char[] inputBuffer;
//
//        subCount = 0;
//        beginOffset = input.getBeginOffset();
//        inputBuffer = input.getBuffer();
//
//        StringSubstutionWithParam sub = new StringSubstutionWithParam(params, 1);
//
//        while (numSubs != 0 && matcher.contains(input, pattern)) {
//            --numSubs;
//            ++subCount;
//            result.append(inputBuffer, beginOffset, input.getMatchBeginOffset() - beginOffset);
//            sub.appendSubstitution(result, matcher.getMatch(), subCount, input, matcher, pattern);
//            beginOffset = input.getMatchEndOffset();
//        }
//        result.append(inputBuffer, beginOffset, input.length() - beginOffset);
//        return subCount;
//    }
//
//    /**
//    public static void main(String[] args) {
//        String comment = "回复 @老徐-_12xx ：回复 @老徐-_12xx2 你好@laoxu_lisp2啊啊啊啊啊 回复 @老徐你好 @王飞@刘超@kenny 你说呢？" + "\n 回复@laoxulisp22: @许峰 @插入";
//         //System.out.println("comm: " +comment);
//         Long start = System.currentTimeMillis();
//         String ss = AtRenderUtils.fetchReplyedUser(comment);
//         Long end = System.currentTimeMillis();
//         System.out.println(ss );
//        // // for (String s : ss) {
//        // System.out.println(ss + " consume time : " + (end - start));
//        // // }
//
//        String comment1 = "回复 @青: @_青-Q- @!青 @雨-_梦 ：@--123@青雨梦@青雨梦 ：回复 @青雨梦-";
//        Set<String> names = AtRenderUtils.fetchAtUserNames(comment1);
//        for (String name : names) {
//            System.out.println(name);
//        }
//        System.out.println(names.size());
//    }
//    */
//
//}

//package com.coolway.common.utils.render;
//
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.oro.text.regex.MatchResult;
//import org.apache.oro.text.regex.Pattern;
//import org.apache.oro.text.regex.PatternMatcher;
//import org.apache.oro.text.regex.PatternMatcherInput;
//import org.apache.oro.text.regex.Substitution;
//
///**
// * 用 replacement内容替换正则匹配到的东西啊
// * 
// * @author zhaopeng.xuzp May 21, 2012 12:07:30 PM
// */
//public class StringSubstutionWithParam implements Substitution {
//
//    private Map<String, String> replacement;
//
//    private int                 matchedOffSet = 0; // 偏移量
//
//    public StringSubstutionWithParam(Map<String, String> replacement, int matchedOffSet){
//        this.setReplacement(replacement);
//        this.setMatchedOffSet(matchedOffSet);
//    }
//
//    public void setReplacement(Map<String, String> replacement) {
//        this.replacement = replacement;
//    }
//
//    public int getMatchedOffSet() {
//        return matchedOffSet;
//    }
//
//    public void setMatchedOffSet(int matchedOffSet) {
//        this.matchedOffSet = matchedOffSet;
//    }
//
//    public Map<String, String> getReplacement() {
//        return replacement;
//    }
//
//    public void appendSubstitution(StringBuffer appendBuffer, MatchResult match, int substitutionCount,
//                                   PatternMatcherInput originalInput, PatternMatcher matcher, Pattern pattern) {
//
//        MatchResult result = null;
//        result = matcher.getMatch();
//        String resultString = result.toString();
//        if (StringUtils.isBlank(resultString) || replacement == null || replacement.isEmpty()) {
//            this.appendOriginalMatched(appendBuffer, resultString);
//        }
//        String key = StringUtils.substring(resultString, matchedOffSet);
//        if (replacement.containsKey(key)) {
//            String remplacementTemplete = replacement.get(key);
//            appendBuffer.append(remplacementTemplete);
//        } else {
//            this.appendOriginalMatched(appendBuffer, resultString);
//        }
//    }
//
//    private void appendOriginalMatched(StringBuffer appendBuffer, String originalMatchedString) {
//        appendBuffer.append(originalMatchedString);
//    }
//}

package net.codeshow.str;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author eric
 * @Version V1.0.0
 * @Date 2021/5/31
 */
public class T30 {

    //时间复杂度O(N^2)
    public List<Integer> findSubstring3(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) return res;
        HashMap<String, Integer> map = new HashMap<>();
        int one_word = words[0].length();
        int word_num = words.length;
        int all_len = one_word * word_num;
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        for (int i = 0; i < s.length() - all_len + 1; i++) {
            String tmp = s.substring(i, i + all_len);
            HashMap<String, Integer> tmp_map = new HashMap<>();
            for (int j = 0; j < all_len; j += one_word) {
                String w = tmp.substring(j, j + one_word);
                tmp_map.put(w, tmp_map.getOrDefault(w, 0) + 1);
            }
            if (map.equals(tmp_map)) res.add(i);
        }
        return res;
    }


    //这个方法是滑动串口的解法，最优
    public static List<Integer> solution(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        Map<String, Integer> wordsMap = new HashMap<>();
        if (s.length() == 0 || words.length == 0) return res;
        for (String word : words) {
            // 主串s中没有这个单词，直接返回空
            if (!s.contains(word)) return res;
            // map中保存每个单词，和它出现的次数
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }
        // 每个单词的长度， 总长度
        int oneLen = words[0].length(), wordsLen = oneLen * words.length;
        // 主串s长度小于单词总和，返回空
        if (wordsLen > s.length()) return res;
        // 只讨论从0，1，...， oneLen-1 开始的子串情况，
        // 每次进行匹配的窗口大小为 wordsLen，每次后移一个单词长度，由左右窗口维持当前窗口位置
        for (int i = 0; i < oneLen; ++i) {
            // 左右窗口
            int left = i, right = i, count = 0;
            // 统计每个符合要求的word
            Map<String, Integer> subMap = new HashMap<>();
            // 右窗口不能超出主串长度
            while (right + oneLen <= s.length()) {
                // 得到一个单词
                String word = s.substring(right, right + oneLen);
                // 右窗口右移
                right += oneLen;
                // words[]中没有这个单词，那么当前窗口肯定匹配失败，直接右移到这个单词后面
                if (!wordsMap.containsKey(word)) {
                    left = right;
                    // 窗口内单词统计map清空，重新统计
                    subMap.clear();
                    // 符合要求的单词数清0
                    count = 0;
                } else {
                    // 统计当前子串中这个单词出现的次数
                    subMap.put(word, subMap.getOrDefault(word, 0) + 1);
                    ++count;
                    // 如果这个单词出现的次数大于words[]中它对应的次数，又由于每次匹配和words长度相等的子串
                    // 如 ["foo","bar","foo","the"]  "| foobarfoobar| foothe"
                    // 第二个bar虽然是words[]中的单词，但是次数超了，那么右移一个单词长度后 "|barfoobarfoo|the"
                    // bar还是不符合，所以直接从这个不符合的bar之后开始匹配，也就是将这个不符合的bar和它之前的单词(串)全移出去
                    while (subMap.getOrDefault(word, 0) > wordsMap.getOrDefault(word, 0)) {
                        // 从当前窗口字符统计map中删除从左窗口开始到数量超限的所有单词(次数减一)
                        String w = s.substring(left, left + oneLen);
                        subMap.put(w, subMap.getOrDefault(w, 0) - 1);
                        // 符合的单词数减一
                        --count;
                        // 左窗口位置右移
                        left += oneLen;
                    }
                    // 当前窗口字符串满足要求
                    if (count == words.length) res.add(left);
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
//        String s = "foobarfoobarfoothe";
//        String[] words = new String[]{"foo", "bar", "foo", "the"};
        String s = "barfoothefoobarman";
        String[] words = new String[]{"foo", "bar"};
        List<Integer> list = solu(s, words);
        System.out.println(list);


    }


    public static List<Integer> mySolution(String s, String[] words) {
        ArrayList<Integer> res = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return res;
        }
        //保存原始的单词出现的次数
        HashMap<String, Integer> wordsMap = new HashMap<>();
        //遍历原始单词
        for (String word : words) {
            //如果s中不包含某个单词，直接返回res
            if (!s.contains(word)) {
                return res;
            }
            //保存出现次数
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }

        //多起点滑动窗口具体实现
        //每个单词的长度
        int wordLen = words[0].length();
        //原始单词的个数
        int wordNum = words.length;
        for (int i = 0; i < wordLen; i++) {
            //用于统计s中的单词出现的次数
            HashMap<String, Integer> tmpMap = new HashMap<>();
            //滑动窗口的左右位置,符合条件的单词的个数count
            int left = i, right = i, count = 0;
            while (right + wordLen <= s.length()) {
                //获取一个单词
                String word = s.substring(right, right + wordLen);
                //扩大滑动窗口的右边界
                right += wordLen;
                //更新这个单词的出现次数
                tmpMap.put(word, tmpMap.getOrDefault(word, 0) + 1);
                if (wordsMap.containsKey(word)) {
                    //如果原始单词中有当前的这个单词，就把count加1
                    count++;
                } else {
                    //如果这个单词没有出现在words中，那么滑动窗口可以向右滑动了
                    left = right;
                    tmpMap.clear();
                    count = 0;
                    continue;

                }
                //如果当前的单词的出现次数超过了原始单词出现的次数
                while (tmpMap.get(word) > wordsMap.get(word)) {
                    //获取当前滑动窗口最左边的那个单词
                    String leftWord = s.substring(left, left + wordLen);
                    //把这个单词出现的次数减1
                    tmpMap.put(leftWord, tmpMap.getOrDefault(leftWord, 0) - 1);
                    //滑动窗口左边界右移
                    left += wordLen;
                    count--;

                }
                if (count == wordNum) {
                    res.add(left);
                }
            }
        }
        return res;

    }


    public static List<Integer> solu(String s, String[] words) {
        ArrayList<Integer> res = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return res;
        }
        //统计原始单词出现的次数
        HashMap<String, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            //如果目标字符串中不存在某个单词，直接返回
            if (!s.contains(word)) {
                return res;
            }
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }
        //原始每个单词的长度
        int wordLen = words[0].length();
        //原始单词个数
        int wordNum = words.length;

        //多起点滑动窗口
        for (int i = 0; i < wordLen; i++) {
            //滑动窗口的左右边界以及符合条件的单词个数
            int left = i, right = i, count = 0;
            //统计单词出现次数
            HashMap<String, Integer> subMap = new HashMap<>();
            while (right + wordLen <= s.length()) {
                //获取滑动窗口最右侧的单词
                String w = s.substring(right, right + wordLen);
                //滑动窗口右移
                right += wordLen;
                //如果原始单词中没有包含当前单词
                if (!wordsMap.containsKey(w)) {
                    left = right;
                    count = 0;
                    subMap.clear();
                } else {
                    //统计单词出现次数
                    subMap.put(w, subMap.getOrDefault(w, 0) + 1);
                    count++;
                    //如果当前单词出现次数大于原始单词出现次数
                    while (subMap.get(w) > wordsMap.get(w)) {
                        //获取滑动窗口最左侧的单词
                        String leftWord = s.substring(left, left + wordLen);
                        subMap.put(leftWord, subMap.getOrDefault(leftWord, 0) - 1);
                        count--;
                        //滑动窗口左边界右移
                        left += wordLen;

                    }
                }
                if (count == wordNum) {
                    res.add(left);
                }

            }
        }
        return res;
    }


}

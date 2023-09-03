package com.pp.community.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author P_P
 * @create 2023-09-03 8:10
 */
@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    /**
     * 前缀树
     */
    private class TrieNode{
        // 关键字结束标识
        private boolean isKeyWordEnd = false;

        /**
         * 子节点(key,value)
         * key：下级节点字符
         * value：下级节点
         */
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        /**
         * 添加子节点
         * @param c 节点字符
         * @param node 节点
         */
        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c, node);
        }

        /**
         * 获取子节点数据
         * @param key
         * @return
         */
        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }


        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
    }

}

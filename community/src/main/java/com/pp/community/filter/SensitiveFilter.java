package com.pp.community.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    // 敏感词替换符号
    private static final String REPLACEMENT = "***";
    // 根节点
    private TrieNode rootNode = new TrieNode();
    /**
     * @PostConstruct : 该注解表示，此方法会在Spring容器调用当前Bean的实例化方法之后调用
     * 初始化前缀树
     * 根据sensitive-words.txt文件中定义的敏感词，创建前缀树
     */
    @PostConstruct
    public void init(){
        // 通过类加载器获取文件
        try(
                // 获取字符串输入流
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                // 将字符串流转换为 字符流
                InputStreamReader isr = new InputStreamReader(is);
                // 将字符流转换为 缓冲字符流
                BufferedReader reader = new BufferedReader(isr)
        ){
            // 读取数据
            String keyword;
            while ((keyword = reader.readLine()) != null){
                // 添加到前缀树
                this.addKeyWord(keyword);
            }
        }catch (IOException e){

        }
    }

    /**
     * 将一个敏感词添加到前缀树
     * @param keyword
     */
    private void addKeyWord(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            // 将字符挂在当前节点的下面
            // 试图获取子节点
            TrieNode subNode = tempNode.getSubNode(c);
            // 没有子节点，需要初始化
            if (subNode == null){
                subNode = new TrieNode();
                // 在根节点后面挂在刚刚初始化的节点
                tempNode.addSubNode(c,subNode);
            }
            // 子节点存在的情况
            // 指向子节点，进入下一轮循环(进入下一层)
            tempNode = subNode;

            // 设置结束的标识
            if (i == keyword.length() - 1){
                // 这是一个敏感词
                tempNode.setKeyWordEnd(true);
            }
        }
    }

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

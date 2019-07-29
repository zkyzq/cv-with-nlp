# beam search
- 常用于文本生成，如在seq2seq中，在生成新序列时需要从词表中选择结果，可以使用beam search来实现，可认为是维特比算法的贪心搜索形式（维特比算法因为使用了动态规划，在词表较大时效率很低）。贪心搜索指的就是最常见的直接从词表中选择概率最大的那个词作为当前时间步的输出，可以认为是beamsearch中beam_size=1的特例。
- **用于预测阶段，训练阶段无需使用**
- 举例说明：
以seq2seq解码器生成语句为例，假设词表为[a, b, c]，beam_size=2
  - 第一步：第一个cell输出，从词表中选择概率最大的两个词，假设是'a'和'b'
  - 第二步：第二个cell输出，结合第一步中的词，组成6个组合：'aa','ab','ac','ba','bb','bc'，选择其中概率最高的两个词，假如是'ab'和'bc'
  - 不断循环，直至遇到结束符号<EOS>,输出两个概率最高的序列
  
```
from math import log
from numpy import array
from numpy import argmax

# GreedySearch
def greedy_decoder(data):
    # 每一行最大概率词的索引
    return [argmax(s) for s in data]
    
# BeamSearch
def beam_search_decoder(data, k):
    sequences = [[list(), 1.0]]
    for row in data:
        all_candidates = list()
        for i in range(len(sequences)):
            seq, score = sequences[i]
            for j in range(len(row)):
                candidate = [seq + [j], score * -log(row[j])]
                all_candidates.append(candidate)
        # 所有候选根据分值排序
        ordered = sorted(all_candidates, key=lambda tup:tup[1])
        # 选择前k个
        sequences = ordered[:k]
    return sequences

# 定义一个句子，长度为10，词典大小为5
data = [[0.1, 0.2, 0.3, 0.4, 0.5],
        [0.5, 0.4, 0.3, 0.2, 0.1],
        [0.1, 0.2, 0.3, 0.4, 0.5],
        [0.5, 0.4, 0.3, 0.2, 0.1],
        [0.1, 0.2, 0.3, 0.4, 0.5],
        [0.5, 0.4, 0.3, 0.2, 0.1],
        [0.1, 0.2, 0.3, 0.4, 0.5],
        [0.5, 0.4, 0.3, 0.2, 0.1],
        [0.1, 0.2, 0.3, 0.4, 0.5],
        [0.5, 0.4, 0.3, 0.2, 0.1]]
data = array(data)
# 解码
result = beam_search_decoder(data, 3)
# print result
for seq in result:
    print(seq)
```

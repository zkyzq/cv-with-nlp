# -*- coding:utf-8 -*-
import numpy as np
import tensorflow as tf
import matplotlib as mpl
mpl.use('Agg')
from matplotlib import pyplot as plt

# param config
HIDDEN_SIZE = 30    # 隐藏节点数，也就是隐藏向量的维数
NUM_LAYERS = 2
TIMESTEPS = 10
TRAINING_STEPS = 1000
BATCH_SIZE = 32

TRAINING_EXAMPLES = 10000
TESTING_EXAMPLES = 1000
SAMPLE_GAP = 0.01

def generate_data(seq):
    '''制造数据集
    Args：
        seq ()
    Returns：
    '''
    X = []
    y = []
    for i in range(len(seq)-TIMESTEPS):
        X.append([seq[i: i + TIMESTEPS]])
        y.append([seq[i + TIMESTEPS]])
    return np.array(X, dtype=np.float32), np.array(y, dtype=np.float32)

def lstm_model(X, y, is_training):
    '''lstm模型建立步骤
       1、建立cell
       2、将cell连成网络
       3、定义前向传播计算predictions及loss定义
       4、创建优化器及优化步骤train_op
       Args：BATCH_SIZE的数据X及y
       Returns：预测结果predictions、损失函数结果loss、优化器操作train_op
    '''
    # 多层LSTM结构
    cell = tf.nn.rnn_cell.MultiRNNCell([tf.nn.rnn_cell.BasicLSTMCell(HIDDEN_SIZE) for _ in range(NUM_LAYERS)])
    # 使用Tensorflow接口将LSTM结构连成网络,并计算前向传播结果
    outputs, states = tf.nn.dynamic_rnn(cell, X, dtype=tf.float32)
    # X:[BATCH_SIZE,TIMESTEPS,input_size], outputs:[BATCH_SIZE,TIMESTEPS,HIDDEN_SIZE]
    # outputs:[BATCH_SIZE,TIMESTEPS,HIDDEN_SIZE]
    # 多层RNN中的states:NUM_LAYERS个[BATCH_SIZE,HIDDEN_SIZE]的tensor,也就是最后一步的隐藏状态ht
    # 多层LSTM中的states:输出NUM_LAYERS个LSTMtumple，每个tumple包含两个信息h和c，分别都是[BATCH_SIZE,HIDDEN_SIZE]的形状
    print('outputs.shape:', outputs)
    print('states.shape:', states)
    # 选取最后一个时刻的也就是最终的输出
    output = outputs[:-1,:]

    # 对输出加上全连接层,计算损失函数：均方误差损失
    predictions = tf.contrib.layers.fully_connected(output, 1, activation_fn = None)
    if not is_training:
        return predictions, None, None
    loss = tf.losses.mean_squared_error(labels=y, predictions=predictions)

    # 创建模型优化器并得到优化步骤
    train_op = tf.contrib.layers.optimize_loss(loss, tf.train.get_global_step(),
                                               optimizer='Adagrad', learning_rate=0.1)
    return predictions, loss, train_op

def train(sess, train_X, train_y):
    # 训练数据以数据集形式提供给计算图，所以没有用feed_dict???
    ds = tf.data.Dataset.from_tensor_slices((train_X, train_y))
    ds = ds.repeat().shuffle(1000).batch(BATCH_SIZE)
    X, y = ds.make_one_shot_iterator().get_next()

    # 得到预测结果、损失函数和训练操作
    with tf.variable_scope("model"):
        predictions, loss, train_op = lstm_model(X, y , True)
    
    # 会话：变量初始化及计算
    sess.run(tf.global_variables_initializer())
    for i in range(TRAINING_STEPS):
        _, l = sess.run([train_op, loss]) # 此处l是loss
        if i % 100 == 0:
            print("train step:" + str(i) + ",loss:" + str(l))

def run_eval(sess, test_X, test_y):
    # 验证，每次讲单个样本输入模型
    ds = tf.data.Dataset.from_tensor_slices((test_X, test_y))
    ds = ds.batch(1) # 验证时BATCH_SIZE=1
    X, y = ds.make_one_shot_iterator().get_next()

    with tf.variable_scope("model", reuse = True):
        prediction, _ , _ = lstm_model(X, [0.0], False) # 验证时y不需要输入，训练时才需要
    
    predictions = []
    labels = []
    for i in range(TESTING_EXAMPLES):
        p, l = sess.run([prediction, y]) # 此处l是label
    predictions.append(p)
    labels.append(l)

    # 计算rmse作为评价指标
    predictions = np.array(predictions).squeeze()
    labels = np.array(labels).squeeze()
    rmse = np.sqrt(((predictions - labels) ** 2).mean(axis=0))
    print("Mean Square Error is:%f"%rmse)

test_start = (TRAINING_EXAMPLES + TIMESTEPS) * SAMPLE_GAP
test_end = test_start + (TESTING_EXAMPLES + TIMESTEPS) * SAMPLE_GAP
train_X, train_y = generate_data(np.sin(np.linspace(0, test_start, TRAINING_EXAMPLES + TIMESTEPS, dtype=np.float32)))
test_X, test_y = generate_data(np.sin(np.linspace(test_start, test_end, TESTING_EXAMPLES + TIMESTEPS, dtype=np.float32)))

with tf.Session() as sess:
    train(sess, train_X, train_y)
    run_eval(sess, test_X, test_y)

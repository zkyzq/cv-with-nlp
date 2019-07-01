
# 初始化参数,每个样本x为1*n列表形式
w = zeros((n,1)) # 线性部分权重,n为特征个数
w_0 = 0 # 偏置初始化
v = normalvariate(0, 0.2) * ones((n, k))
for it in range(self.iter): # 迭代次数
    # 对每一个样本
    for x in range(m) # m为样本集
        # xi与vi矩阵相乘
	inter_1 = mat(x) * v
	# xi^2*vi^2
        inter_2 = multiply(mat(x), mat(x)) * multiply(v,v)#注意np.multiply,np.dot和*区别
	# 获得交叉项
	interaction = sum(multiply(inter_1,inter_1) - inter_2) / 2
        # 计算预测
        pred = w_0 + mat(x) * w + interaction
        print("预测的输出pred:", pred)
        # 计算sigmoid(y*pred_y)-1准确的说不是loss，只是作为更新w的中间参数，二分类的损失loss的梯度可以表示为：gradient = (self.sigmoid(classLabels[x] * p[0, 0]) -1)*classLabels[x]*p_derivative，其中 p_derivative 代表常数项、一次项、交叉项的导数
        loss = self.sigmoid(classLabels[x] * p[0, 0]) - 1
        if loss >= -1: # 暂时不明白这段作用 
            loss_res = '正方向 '
        else:
            loss_res = '反方向'
        # 更新参数
        w_0 = w_0 - self.alpha * loss * classLabels[x] # 常数项系数更新
        for i in range(n):
            if dataMatrix[x, i] != 0:
                w[i, 0] = w[i, 0] - self.alpha * loss * classLabels[x] * dataMatrix[x, i] # 一次项系数更新
                for j in range(k):
                    v[i, j] = v[i, j] - self.alpha * loss * classLabels[x] * (
                            dataMatrix[x, i] * inter_1[0, j] - v[i, j] * dataMatrix[x, i] * dataMatrix[x, i]) # 交叉项系数更新

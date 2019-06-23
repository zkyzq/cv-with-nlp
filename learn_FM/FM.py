
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

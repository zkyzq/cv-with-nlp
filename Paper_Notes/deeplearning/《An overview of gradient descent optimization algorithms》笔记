
## 梯度下降形式
根据更新时使用的样本数量可以分为三种：BGD（Batch gradient descent，批梯度下降）SGD（Stochastic gradient descent，随机梯度下降）和Mini-batch gradient descent。不同方式在参数更新精度和参数更新耗时间有所不同。  
***注： epoch、iteration和batch_size的关系***  
**batch_size:** 批大小，每次选取batch_size大小的样本进行梯度计算  
**iteration:** 一次迭代，根据batch_size的样本大小进行一次参数更新  
**epoch:** 一次全量数据上的更新完成就叫一个epoch  
#### BGD
```math
θ=θ−η·∇_{θ}J(θ)
```
每次在全量数据集上计算梯度进行更新，每次迭代iteration都是一个epoch。  
- **优点：** 梯度下降方向准确，在凸函数上保证收敛至全局最优点，在非凸函数上保证收敛到局部最优点。  
- **缺点：** 收敛到相同程度速度慢（需要的epoch多）；全量数据就非常消耗内存；无法进行线上更新。  
- **代码实现：**
```
for i in range(n_epochs):
    # 首先在全量数据上计算参数梯度向量params_grad
    params_grad =  evaluate_gradient(loss_function , data, params)
    # 然后更新参数
    params = params - learning_rate * params_grad
    # n_epochs是提前确定好的参数，params是需要更新的参数，learning_rate是学习率，
```

#### SGD

```math
θ = θ − η · ∇_θ J(θ;x^{(i)} ;y^{(i)} ).......x^{(i)} ;y^{(i)} 代表第i个样本
```
每次使用单个数据进行梯度计算并更新参数，相当于batch_size=1。
- **优点:** 可在线训练；训练收敛速度快
- **缺点:** 一方面容易产生震荡，甚至两次更新的梯度可能相互抵消，另一方面有可能跳出局部最优点到更好的最优点（如全局最优点）；学习率下降到一定程度时就可以收敛到和BGD相同的程度。  
***注：每个epoch前要进行shuffle, 以此避免样本的排列顺序对梯度下降计算有影响的情况***
- **代码实现：**
```
for i in range(nb_epochs):
    # 首先进行shuffle
    np.random.shuffle(data)
    # 使用每个样本进行更新
    for example in data:
        params_grad = evaluate_gradient(loss_function , example , params)
        params = params - learning_rate * params_grad
```
#### Mini-Batch gradient descent
```math
θ = θ − \eta·∇_θ J(θ;x^{(i:i+n)} ;y^{(i:i+n)} ) 
```
中和BGD和SGD，每个iteration采用n个样本进行计算更新。
- **优点：** 兼具两种方法优点
- **代码实现：** 
```
for i in range(nb_epochs):
    # 同样先进行shuffle
    np.random.shuffle(data)
    # 一般batch_size大小50~256
    for batch in get_batches(data, batch_size=50):
        params_grad = evaluate_gradient(loss_function , batch , params)
        params = params - learning_rate * params_grad
```

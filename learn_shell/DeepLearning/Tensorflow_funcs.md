## tf.Variable()和tf.get_variable()
参考：https://blog.csdn.net/TeFuirnever/article/details/89577480
- **主要区别**：
1. **get_variable()初始化更方便。** tf.Variable()是定义变量，用于生成一个初始值为initial-value的变量；必须指定初始化值;tf.get_variable()获取已存在的变量(要求不仅名字，而且初始化方法等各个参数都一样)，如果不存在，就新建一个；可以用各种初始化方法initializer，不用明确指定值。
```
a = tf.Variable(initial_value=tf.random_normal(shape=[200, 100], stddev=0.1), trainable=True)
b = tf.get_variable(name = 'weights', shape=[200, 100], dtype=tf.float32, initializer=tf.random_normal_initializer(stddev=0.1))
```
2. **tf.get_variable()方便参数共享。** tf.Variable()遇到命名冲突会自我管理；而tf.get_variable()会报错。因此，在共享变量的时候需要使用tf.get_variable()。在其他情况下，这两个的用法是一样的。
```
import tensorflow as tf
w_1 = tf.Variable(3,name="w_1")
w_2 = tf.Variable(1,name="w_1")
print w_1.name
print w_2.name
#输出
#w_1:0
#w_1_1:0
```
```
import tensorflow as tf
 
w_1 = tf.get_variable(name="w_1",initializer=1)
w_2 = tf.get_variable(name="w_1",initializer=2)
#错误信息
#ValueError: Variable w_1 already exists, disallowed. Did
#you mean to set reuse=True in VarScope?
```
需要注意的是tf.get_variable()，要配合reuse和tf.variable_scope()使用，对于get_variable()来说，如果已经创建的变量对象，就把那个对象返回，如果没有创建变量对象的话，就创建一个新的。
```
import tensorflow as tf

with tf.variable_scope("scope1"):
    w1 = tf.get_variable("w1", shape=[])
    w2 = tf.Variable(0.0, name="w2")
with tf.variable_scope("scope1", reuse=True):
    w1_p = tf.get_variable("w1", shape=[])
    w2_p = tf.Variable(1.0, name="w2")

print(w1 is w1_p, w2 is w2_p)
#输出
#True  False
```
#### tf.Variable()
- 格式
```
tf.Variable(
	initial_value=None, #  
	trainable=True, 
	collections=None, 
	validate_shape=True, 
	caching_device=None, 
	name=None, 
	variable_def=None, 
	dtype=None, 
	expected_shape=None, 
	import_scope=None
)
```
- 参数说明
  - `initial_value`：Tensor或可转换为Tensor的Python对象，它是Variable的初始值。除非validate_shape设置为False，否则初始值必须具有指定的形状；也可以是一个可调用的，没有参数，在调用时返回初始值。在这种情况下，必须指定dtype。 （请注意，init_ops.py中的初始化函数必须首先绑定到形状才能在此处使用。）

  - `trainable`：如果为True，则会默认将变量添加到图形集合GraphKeys.TRAINABLE_VARIABLES中。此集合用于Optimizer类优化的的默认变量列表【可为optimizer指定其他的变量集合】，可就是要训练的变量列表。

  - `collections`：一个图graph集合列表的关键字。新变量将添加到这个集合中。默认为[GraphKeys.GLOBAL_VARIABLES]。也可自己指定其他的集合列表。

  - `validate_shape`：如果为False，则允许使用未知形状的值初始化变量。如果为True，则默认为initial_value的形状必须已知。

  - `caching_device`：可选设备字符串，描述应该缓存变量以供读取的位置。默认为Variable的设备。如果不是None，则在另一台设备上缓存。典型用法是在使用变量驻留的Ops的设备上进行缓存，以通过Switch和其他条件语句进行重复数据删除。

  - `name`：变量的可选名称。默认为“Variable”并自动获取。

  - `variable_def`：VariableDef协议缓冲区。如果不是None，则使用其内容重新创建Variable对象，引用图中必须已存在的变量节点。图表未更改。variable_def和其他参数是互斥的。

  - `dtype`：如果设置，则initial_value将转换为给定类型。如果为None，则保留数据类型（如果initial_value是Tensor），或者convert_to_tensor将决定。

  - `expected_shape`：TensorShape。如果设置，则initial_value应具有此形状。

  - `import_scope`：可选字符串。要添加到变量的名称范围。仅在从协议缓冲区初始化时使用

一般常用的参数包括初始化值和名称name(是该变量的唯一索引)，在使用变量之前必须要进行初始化，初始化的方式有三种：

1. 在会话中运行initializer操作。
2. 从文件中恢复，如restore from checkpoint。
3. 自己通过tf.assign()给变量附初值。
#### tf.get_variable()
- 格式
```
get_variable(
    name,
    shape=None,
    dtype=None,
    initializer=None,
    regularizer=None,
    trainable=True,
    collections=None,
    caching_device=None,
    partitioner=None,
    validate_shape=True,
    use_resource=None,
    custom_getter=None,
    constraint=None
)
```
- 参数说明
  - name：新变量或现有变量的名称。

  - shape：新变量或现有变量的形状。

  - dtype：新变量或现有变量的类型（默认为DT_FLOAT）。

  - initializer：如果创建了，则用它来初始化变量。

  - regularizer：A（Tensor - > Tensor或None）函数;将它应用于新创建的变量的结果将添加到集合tf.GraphKeys.REGULARIZATION_LOSSES中，并可用于正则化。

  - trainable：如果为True，还将变量添加到图形集合GraphKeys.TRAINABLE_VARIABLES（参见tf.Variable）。

  - collections：要将变量添加到的图表集合列表。默认为[GraphKeys.GLOBAL_VARIABLES]（参见tf.Variable）。

  - caching_device：可选的设备字符串或函数，描述变量应被缓存以供读取的位置。默认为Variable的设备。如果不是None，则在另一台设备上缓存。典型用法是在使用变量驻留的Ops的设备上进行缓存，以通过Switch和其他条件语句进行重复数据删除。

  - partitioner：可选callable，接受完全定义的TensorShape和要创建的Variable的dtype，并返回每个轴的分区列表（当前只能对一个轴进行分区）。

  - validate_shape：如果为False，则允许使用未知形状的值初始化变量。如果为True，则默认为initial_value的形状必须已知。

  - use_resource：如果为False，则创建常规变量。如果为true，则使用定义良好的语义创建实验性ResourceVariable。默认为False（稍后将更改为True）。在Eager模式下，此参数始终强制为True。

  - custom_getter：Callable，它将第一个参数作为true getter，并允许覆盖内部get_variable方法。 custom_getter的签名应与此方法的签名相匹配，但最适合未来的版本将允许更改：def custom_getter（getter，* args，** kwargs）。也允许直接访问所有get_variable参数：def custom_getter（getter，name，* args，** kwargs）。一个简单的身份自定义getter只需创建具有修改名称的变量是：python def custom_getter（getter，name，* args，** kwargs）：return getter（name +'_suffix'，* args，** kwargs）。

如果initializer初始化方法是None(默认值)，则会使用variable_scope()中定义的initializer，如果也为None，则默认使用glorot_uniform_initializer，也可以使用其他的tensor来初始化，value、和shape与此tensor相同。

正则化方法默认是None，如果不指定，只会使用variable_scope()中的正则化方式，如果也为None，则不使用正则化。

## tf.strided_slice()
- 功能：取tensor中的某一部分  
- 格式：tf.strided_slice(input, start, end, stride)
- 参数：input为输入start为开始的索引，stride为步长，直到end，取`[start,end)`对应的部分。start、stride和end可以为对应长度的list，分别对应着不同的维数
- 示例：
```
>>> t = tf.constant([[[1, 1, 1], [2, 2, 2]],
...                  [[3, 3, 3], [4, 4, 4]],
...                  [[5, 5, 5], [6, 6, 6]]])
>>> a = tf.strided_slice(t, [1, 0, 0], [2, 1, 3], [1, 1, 1])
>>> b = tf.strided_slice(t, [1, 0, 0], [2, 2, 3], [1, 1, 1])
>>> c = tf.strided_slice(t, [1, -1, 0], [2, -3, 3], [1, -1, 1])
>>> with tf.Session() as sess:
...     sess.run(a)
...     sess.run(b)
...     sess.run(c)
...
# 结果: 
array([[[3, 3, 3]]])
array([[[3, 3, 3],
        [4, 4, 4]]])
array([[[4, 4, 4],
        [3, 3, 3]]])
```
- 示例解释：
一个维度一个维度来看。  
  - 0维: start[0]=1,start[0]+stride[0]=end[0]=2,所以取t1=t[[1,2),:,:]，得到[[[3, 3, 3], [4, 4, 4]]]
  - 1维: start[1]+stride[1]=end[1],所以取t2=t1[:,[0,1),:]=[[[3,3,3]]]
  - 2维: 
    - tmp1=start[2]+stride[2]=1<end[2]
    - tmp2=tmp+stride[2]=2<end[2]继续下一轮
    - tmp3=tmp+stride[2]=3=end[2]结束
    - 输出t3=t2[:,:,[0,1,2]]=[[[3,3,3]]]  
## tf.tile()
按指定形状扩展张量
- 格式：  
tf.tile(input,multiples,name=None)  
input是待扩展的张量，multiples是扩展方法。 
假如input是一个3维的张量。那么mutiples就必须是一个1x3的1维张量。这个张量的三个值依次表示input的第1，第2，第3维数据扩展几倍。
- 示例：
```
import tensorflow as tf

a = tf.constant([[1, 2], [3, 4], [5, 6]], dtype=tf.float32)
a1 = tf.tile(a, [2, 3])
with tf.Session() as sess:
    print(sess.run(a))
    print(sess.run(a1))
```
结果:
```
# 原始二维张量
[[ 1.  2.]
 [ 3.  4.]
 [ 5.  6.]]

# 结果二维张量
[[ 1.  2.  1.  2.  1.  2.]
 [ 3.  4.  3.  4.  3.  4.]
 [ 5.  6.  5.  6.  5.  6.]
 [ 1.  2.  1.  2.  1.  2.]
 [ 3.  4.  3.  4.  3.  4.]
 [ 5.  6.  5.  6.  5.  6.]]

#张量形状
>>> print(a.shape)
(3, 2)
>>> print(a1.shape)
(6, 6)
```
## tf.sequence_mask()
返回一个表示每个单元的前N个位置的mask张量.
- 格式:
```
sequence_mask(
    lengths,
    maxlen=None,
    dtype=tf.bool,
    name=None
)
```
- 参数:  
  - lengths：整数张量,其所有值小于等于maxlen.
  - maxlen：标量整数张量,返回张量的最后维度的大小；默认值是lengths中的最大值.
  - dtype：结果张量的输出类型.
  - name：操作的名字.
- 示例：
```
>>> with tf.Session() as sess:
...     a=tf.sequence_mask([1,2,3],5)
...     b = tf.sequence_mask([[1, 2], [3, 4]])
...     sess.run(a)
...     sess.run(b)
...
array([[ True, False, False, False, False],
       [ True,  True, False, False, False],
       [ True,  True,  True, False, False]], dtype=bool)

# b
[[[ True False False False]
  [ True  True False False]]
 [[ True  True  True False]
  [ True  True  True  True]]]
```

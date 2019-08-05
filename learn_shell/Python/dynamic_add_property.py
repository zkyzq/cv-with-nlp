# 为l类动态添加属性及__slots__的使用

# 定义类
class Cat:
    def __init__(self, name):
        self.__name = name

# 类外函数，作为动态添加的对象
def action_func(Cat_obj) # 这里可以改成self，应该都一样吧
    print("{} is eating meat".format(Cat_obj.name))

# 测试为类动态添加属性
cat1 = Cat('Tom')
cat2 = Cat('Jerry')
Cat.action = action_func
cat1.action()
cat2.action()
'''
输出：
Tom is eating meat
Jerry is eating meat
说明已经将函数添加到类中，所有实例都可以使用该方法
但是这样很不安全，可以使用__slots__来限制为某个类的实例动态添加方法和属性，但不限制对类动态添加
'''

# __slots__属性是一个元组，里边限制了可以为类的实例动态添加属性和方法的范围
class Dog:
    __slots__ = ('walk', 'age', 'name')
    def __init__(self, name):
        self.name = name
    def test():
        print('预先定义的test方法')
d = Dog('Snoopy')
# 只允许动态为实例添加walk、age、name这3个属性或方法
def walk(self) # 这里可以改成self，应该都一样吧
    print("{}正在慢慢走".format(self.name))
def fast_walk(self) # 这里可以改成self，应该都一样吧
    print("{}正在很快的走".format(self.name))
d.walk = walk
d.age = 5
d.walk()
Dog.walk = fast_walk
d.walk()
Dog.father = 'SNOOPY'
d.father()
d.foo = 30 # 此处报错

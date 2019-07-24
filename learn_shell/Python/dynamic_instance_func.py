# -*- encoding:utf-8 -*-
class Person :
    '这是一个学习Python定义的一个Person类'
    # 下面定义了一个类变量
    hair = 'black'
    def __init__(self, name = 'Charlie', age=8):
        # 下面为Person对象增加2个实例变量
        self.name = name
        self.age = age
    # 下面定义了一个say方法
    def say(self, content):
        print(content)

def info(self):
    print "——————info函数——————",self
if __name__ == '__main__':
    p = Person()
    p.say("This is a test.")
    
    '''实例动态绑定函数'''
    # 动态添加实例方法info为foo(动态绑定方法)
    p.foo = info
    #动态添加的方法在调用时必须明确调用的实例也就是self
    p.foo(p)
    #使用lambda函数为实例方法bar动态赋值-py2好像没有
    #p.bar = lambda self:print "————bar函数————",self
    #p.bar(p)

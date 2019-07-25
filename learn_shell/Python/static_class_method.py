# -*- encoding:utf-8 -*-
class Bird:
    # classmethod修饰类方法,第一个参数自动绑定Bird类本身
    @classmethod
    def fly(cls):
        print "类方法fly()",cls
    #staticmethod修饰静态方法，参数不会被绑定
    @staticmethod
    def info(a):
        print "静态方法info()",a


if __name__ == '__main__':
    
    # 类调用方法
    Bird.fly() # 类方法自动绑定
    Bird.info("奥术大师多") # 静态方法无自动绑定，如不指定会报错

    # 实例调用方法
    b = Bird() 
    b.fly() # 其实还是调用Bird类方法，情况与上相同
    b.info("asdasd") # 不指定参数会报错
    # 类方法用途：如一个学生类，同时一个班每个同学有一个学生实例，想要计算整个班的人数，则可以通过类方法进行累计
    # 静态方法用途：不需要实例，逻辑上属于类，但和类本身没有关系，仅托管于某个类的名称空间，便于使用和维护

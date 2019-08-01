'''
- 作用(个人感觉有点鸡肋，但很常见)：  
 将类的getter方法（也就是常见的用于获取某个类变量的函数）变为一个属性
- 详细说明及示例：  
  @property修饰的类函数会变成一个属性（可以认为是getter），用于获取某个类变量，尤其是私有和保护变量。使用时可以不加()，直接使用函数名。
- 在使用@property修饰函数func1以后，还会生成一个setter方法，使用@func1.setter（xxx表示类变量名称）修饰另一个函数func1(也就是和@property修饰函数同名)，就可以将xxx变为一个可读写的属性；不使用func1.setter，xxx就是只读的
'''
class Rect:
    def __init__(self,area):
        self.__area = area
    @property
    def area(self):
        return self.__area
   # 使用setter来添加写入操作
    @area.setter
    def area(self,area):
        if area < 100:
            raise ValueError("area must be under 100")
        else:
            self.__area = area
            print("setter后的面积：",self.__area)
if __name__ == "__main__":
    rect = Rect(30)
    # 直接通过方法名来访问area方法
    print("矩形的面积是：",rect.area)
    rect.area = 100
    """输出
    矩形的面积是： 30
    setter后的面积： 100
    """

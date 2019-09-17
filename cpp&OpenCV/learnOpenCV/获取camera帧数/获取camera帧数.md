对于webcamera使用opencv自带的cap.get(cv2.CAP_PROP_FPS)获得的结果并不正确，要自己手动获取。
方法是get固定帧数，并同时计算时间，再将帧数除以时间
最后的结果如下：
```
3.4.2
Frames per second using video.get(cv2.CAP_PROP_FPS) : 12.0
capture120frames
Time taken 3.979783058166504seconds
Estimate frames per second : 30.15239731566783
```

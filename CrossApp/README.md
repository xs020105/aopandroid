
####CrossApp beta 0.2.1 更新内容：  

#####1.添加容器类
添加CAVector、CADeque、CAList、CAMap、CAMultimap。

#####2.添加控件
添加控件CACollectionView,比CATalbeView 更加复杂的控件诞生，强大的功能可满足很多界面需求。

#####3.添加适配修正参数
添加适配修正参数，以iphone4屏幕为基准，可使其他所有移动设备所表现的视图物理尺寸与iphone4完全一致。

#####4.修复bug
修复触摸事件内存泄露的bug

#####5.修复bug
修复CAAlertView特定情况崩溃的bug

#####6.代码优化
优化CATableView代理方法

#####7.文件补全
添加2.0版本丢失的文件


####CrossApp beta 0.2.1 Update:

#####1.Add container class
Add CAVector, CADeque, CAList, CAMap and CAMultimap.

#####2.Add controls
Add control CACollectionView which is more complicated than CATableView, and its great function could meet many interfaces’ demands.

#####3.Add adaptive corrected parameter
Add adaptive corrected parameter and take iPhone4 as standard, and this parameter enables all other mobile devices’ displayed view physical size are same with iPhone4.

#####4.Fixed bug
Fixed bug that touch event’s memory leak.

#####5.Fixed bug
Fixed bug that CAAlertView had breakdown in particular circumstance.

#####6.Codes optimization
Optimized CATableView agent method.

#####7.Files completion
Add lost files of 2.0 versions.


**********************************************************************************


####CrossApp beta 0.2.0（重要更新） 更新内容：  

#####1.整理引擎目录结构
整理目录结构，改名部分类名。

#####2.触摸事件分发优化
触摸事件分发 现在针对滑动容器优化

#####3.添加控件CAAlertView
提示框，按钮个数三个以内横排，超过三个纵向排列，并放入滑动容器中。

#####4.优化CALabel

#####5.补全CATextField功能

#####6.修复部分bug

#####7.添加一键创建工程

#####8.添加一件部署安卓环境, 下载地址：http://pan.baidu.com/s/1qW6ql32


####CrossApp beta 0.2.0 Update: (Important changes)

#####1.Arranged engine directory structure
Arranged directory structure and changed some class name

#####2.Touch event dispatch optimization
We optimized sliding container of touch event dispatch

#####3.Add CAAlertView control
In prompt box, if button number is less than three, they are arranged in horizontal mode, if the number is greater than three, arranged in vertical mode and put into sliding container.

#####4.CALabel optimization

#####5.Complemented CATextField function

#####6.Fixed some bugs

#####7.Add one-key project building

#####8.Add one-key Android environment deployment, download: http://pan.baidu.com/s/1qW6ql32



####CrossApp alpha 0.1.11（重要更新） 更新内容：  

#####1.引擎默认主题风格变更
新的风格将更加漂亮，切适配性更强。

#####2.CAScale9ImageView重写
由于之前的9宫格有功能缺陷，现重写，新的9宫格可以支持单向缩小。

#####3.CALabel优化
CALabel结构优化，性能优化。将来还会优化。



####CrossApp alpha 0.1.11 Update: (Important changes)

#####1.Default engine theme style change
The new style is more attractive and has a greater suitability.

#####2.CAScale9ImageView rewriting
We rewrote 9 rectangle gird because of the previous version had function defects, and the new version supports unidirectional zoom-out.

#####3.CALabel optimization
We optimized CALabel’s structure and performance, and will do it in the future.



####CrossApp alpha 0.1.10 更新内容：  

#####1.CAImageView优化
优化CAImageView，并修改之前在CAImageView添加子视图显示不正确的bug，现在可随意添加子视图给CAImageView。

#####2.删除ccColor3B，并添加CAColor4B
删除之前的ccColor3B，以前所有用到ccColor3B的地方统统被CAColor4B替换，增加透明度属性。

#####3.删除Opacity属性，并添加新的alpha属性
alpha属性为float型，数值范围为0-1.0f，设置alpha属性可直接影响所有子视图的透明度。

#####4.彻底删除position属性
同样 CCMoveTo、CCMoveBy也被引擎所抛弃

#####5.修复Android平台GPS的bug
修复android平台手机因没有开启gps导致应用程序无法运行的bug

#####6.修复CAScrollView的bug
修复之前代理回调方法virtual void scrollViewWillBeginDragging(CAScrollView* view){};
触发时机错误的bug



####CrossApp alpha 0.1.10 Update:

#####1.CAImageView optimization
Optimized CAImageView, and fixed bug that wrong display problem when adding sub view in CAImageView, now you are free to add sub view in CAImageView.

#####2.Delete ccColor3B and add CAColor4B
Delete ccColor3B and replace all ccColor3B with CAColor4B, add transparency attribute.

#####3.Delete Opacity attribute and add new alpha attribute
Alpha attribute is float type with 0 – 1.0f value range, alpha attribute setting can directly influence all sub views’ transparency.

#####4.Completely delete position attribute
CCMoveTo and CCMoveBy are also deleted from our engine

#####5.Fixed the GPS bug of Android platform
Fixed bug that application cannot be started because of Android cellphone did not open GPS

#####6.Fixed CAScrollView bug
Fixed bug that agent callback method ‘void scrollViewWillBeginDragging(CAScrollView* view){};’ has a wrong trigger timing.



####CrossApp alpha 0.1.9 更新内容：  

#####1.触摸事件bug修改
在滑动层上点击按钮瞬间抬起不触发的bug

#####2.CATabBar优化
对CATabBar进行了体验的优化

#####3.CATabBar与CANavigationBar优化
可通过ViewController动态更新其显示数据

#####4.CAButton与CASegmentedControl优化
修改文字显示大小错误的bug

#####6.安卓系统，从后台返回黑屏的bug
之前，在安卓系统中，如果从后台返回，有几率黑屏

#####5.添加动画相关 CCFrameTo 与 CCCenterTo
可以动态的修改 frame与center

#####6.添加viewController悬浮抽屉功能
现在可以调用任何一个viewController的presentModalViewController与dismissModalViewController来显示与撤销。



####CrossApp alpha 0.1.9 Update:

#####1.Touch event bug modification
Fixed bug that Instant release after clicking button on sliding layer is not triggered

#####2.CATabBar optimization
Experience optimization on CATabBar

#####3.CATabBar and CANavigationBar optimization
It’s able to dynamic update CATabBar and CANavigationBar display data via ViewController

#####4.CAButton and CASegmentedControl optimization
Fixed bug that wrong text display size

#####5.Fixed bug that blank screen appears when returning from background in Android system
Previously, if we return from background in Android system, there is certain of probability that blank screen appears

#####6.Add animation related CCFrameTo and CCCenterTo
You can dynamically modify frame and center

#####7.Add suspension drawer function of viewController
You can call any viewController’s presentModalViewController and dismissModalViewController to display and dismiss



####CrossApp alpha 0.1.8 更新内容：  

#####1.
添加CANavigationController左右button的定制

#####2.
CATextField添加win32输入功能

#####3.
添加CANavigationController 替换当前CAViewController的功能

#####4.
修复触摸分发系统在可滑动容器上快速点击按钮无法触发按钮事件的bug

#####6.
修复在win32由于触摸事件造成的偶尔崩溃的bug

#####5.
优化CASegmentedControl

#####6.
优化CANavigationBar在竖屏的高度及布局。



####CrossApp alpha 0.1.8 Update:

#####1.
Add CANavigationController left and right button customization
#####2.
Add win32 input function in CATextField
#####3.
Add CANavigationController, and replace current CAViewController’s function
#####4.
Fixed bug that quickly clicking button on slidable container of touch dispatch system is unable to trigger button event
#####5.
Fixed bug that occasional crashes caused by touch event in win32
#####6.
Optimize CASegmentedControl
#####7.
Optimize CANavigationBar height and layout in portrait mode


####CrossApp alpha 0.1.7 更新内容：  

#####1.
修复CATextField在 win32 与mac平台编译报错的问题

#####2.
修复TabBarController当前viewController不是第一项时 显示与隐藏 tabBar时画面出错的问题


####CrossApp alpha 0.1.7 Update:

#####1.
Fixed CATextField compiling error report problem on win32 and Mac platform.

#####2.
Fixed picture error report problem in displaying and hiding tabBar when current viewController of TabBArController is not the first item.


####CrossApp alpha 0.1.6 更新内容：  
#####1. 添加CASlider：  
滑动条
在此特别鸣谢 9秒ID为juguanhui的 美女程序员，为我们提供了优质的控件源码。

#####2. 添加CASegmentedControl：  
分段选项器
在此特别鸣谢 9秒ID为juguanhui的 美女程序员，为我们提供了优质的控件源码。

#####3. 触摸事件分发规则优化，添加CAResponder类  
经过此次优化，触摸事件分发更加智能，使开发过程变得简单容易。

#####4. CATextField功能补全：  
1.添加输入框选择键盘类型
2.添加遮蔽显示字符为※
3.添加光标移动以及插入删除
4.添加获取键盘高度等功能。
5.目前只支持iOS与Android。

#####5. 添加CATabBarController 、CANavigationController新功能
支持tabBarControll切换viewController动画效果功能
增加 CATabBarController 、CANavigationController 隐藏 其Bar的功能

#####6. 修改部分bug 
1.修正CAView在addSubview之前设置ZOder不起作用的bug
2.修正调用CCDirector中setNotificationNode(CAView* view)不起作用的bug
3.scrollView指示条显示隐藏时机错误的bug

#####7. win32平台向下兼容至VS2012



####CrossApp alpha 0.1.6 Update:
#####1. Add CASlider:
Slider<br/>
Special thanks to a beautiful female programmer (9miao ID: juguanhui) for providing superior controls source codes for us.

#####2. Add CASegmentedControl:
Segmented Controller<br/>
Special thanks to a beautiful female programmer (9miao ID: juguanhui) for providing superior controls source codes for us.

#####3. Touch event dispatch rules optimization, add CAResponder class.
This optimization makes touch event dispatch become more intelligent and development process simple and easy.

#####4. CATextField functions completion:
1.Add keyboard type selecting on text field<br/>
2.Add hiding display character ※<br/>
3.Add cursor movement, insert and delete<br/>
4.Add functions such as obtaining keyboard height and others<br/>
5.Only support iOS and Android for now<br/>

#####5. Add new functions of CATabBarController and CANavigationController
Support tabBarController switching to viewController animation effect<br/>
Add TabBar and NavigationBar hiding function<br/>

#####6. Fixed some bugs
1.Fixed bug: setting ZOder before addSubview in CAView is invalid<br/>
2.Fixed bug: calling setNotificationNode(CAView* view) in CCDirector is invalid<br/>
3.Fixed bug: scrollView indicator's display and hiding timing are wrong<br/>

#####7. Backward compatible to VS2012 on win32 platform



####CrossApp alpha 0.1.5 更新内容：  
#####1. 添加CASwitch：  
在此特别鸣谢 9秒ID为juguanhui的 美女程序员，为我们提供了优质的控件源码。
由于一些原因，juguanhui无法通过git上传代码，所以此次控件源码提交是通过9秒内部上传的。

#####2. 触摸事件分发规则修改：  
新的触摸事件无需注册，只要继承CAView的所有子类，均可通过实现cctouchBegin等函数监听.而且在touchbegin时的视图矩形区域外是无法获得事件监听的。
新的触摸事件是由父视图分发给子视图的，所以当在父视图矩形区域外的touchBegin事件，即使其子视图矩形区域包含这个点，也是无法获取到触摸事件的。

#####3. 增加GPS定位功能：  
目前支持iOS与android平台，wp8无功能

#####4. 增加相册选择照片并返回照片图片数据功能：  
目前支持iOS与android平台，wp8无功能

#####5. 增加从摄像头拍照并返回照片图片数据功能：  
目前支持iOS与android平台，wp8无功能

#####6. 增加获取手机通讯录联系人信息的功能：  
目前支持iOS与android平台，wp8无功能



####CrossApp alpha 0.1.5 Update:
#####1. Add CASwitch:
Special thanks to a beautiful female programmer (9miao ID: juguanhui) for providing superior controls source codes for us. Juguanhui cannot upload codes via git for some reasons, so this time the controls sources code was submitted and uploaded by 9miao worker.

#####2. Touch event dispatch rules modification:
The new touch event does not have to be registered, and you can implement cctouchBegin and other functions monitoring by only inheriting all child classes of CAView. In addition, it’s unable to obtain event monitoring outside of view rectangular region when you touch screen.<br/>

The new touch event is dispatched from parent view to child view, so even if your touchBegin event spot is in child view rectangular region, you cannot obtain touch event on condition that the touchBegin event is outside of parent view rectangular region.<br/>

#####3. Add GPS location function:
Support iOS and Android for now, no WP8.

#####4. Add photo select from album and photo picture data return function:
Support iOS and Android for now, no WP8.

#####5. Add photo taking from webcam and photo picture data return function:
Support iOS and Android for now, no WP8.

#####6. Add mobile address book contacts information obtaining function:
Support iOS and Android for now, no WP8.



####CrossApp alpha 0.1.4 更新内容：  
#####1. 耗电优化：  
由于cocos2d-x的渲染用的渲染驱动模式，程序生命周期中，在不断地重绘，帧数一般在60帧。渲染驱动模式的缺点就是耗电。显而易见，这种不断的重绘方式对于应用程序来说太浪费了。因此，我们针对应用程序的特性，将渲染机制改为事件驱动模式。这种模式的渲染要有外界触发才会重绘，在没有外界触发的时候画面静止，渲染停止，以达到节能的效果  
#####2. CAButton修改：  
增加新的属性 AllowsSelected（包含set，get），默认为false。如果为true，则开启选中模式。在这种模式下。按钮会在选中与默认状态之间切换。即，如果当前为默认状态，按下按钮并抬起，按钮切换到选中状态，再次按下并抬起，则按钮又切换回普通状态。同时，我们可以用getSelected（）方法来及时的获取当前按钮属于2种状态中的哪一种
#####3. CASchedule（经原有CCSchedule修改）：  
**a) 简化用法：**启动停止等操作只需调用相应静态方法即可  
**b) 内存管理修改：**之前启动定时器，针对pTarget的引用计数会+1，以防止因pTarget的释放造成程序崩溃。现在启用了新的机制，已经可以完全避免以上问题，因此在启动定时器后不再对pTarget的引用计数+1，在pTarget释放后自动停止相关定时器  
**c) 新特点：**当pTarget为CAViewController或者其派生类型时，CAViewController的view不显示时，定时器会自动暂停，当重新显示时，会自动恢复。除此之外，在pTarget释放前不会有变化  
#####4. Demo位置变动  
demo由原来的projects文件夹移到samples  



####贡献者名单：juguanhui，


####CrossApp alpha 0.1.4 Update:
#####1.Power Consumption Optimization:
A program is continuously redrawing at general 60 frame rates in its life cycle because of the rendering-driven mode of cocos2d-x rendering, but the shortcoming of this mode is the relatively considerable power consumption. Obviously this continuous redraw mode is quite a waste for program, so we change the rendering mechanism into event-driven mode based on application features. In this rendering mode the redraw is only triggered by outside, if not the screen is static and rendering stops, so as to achieve energy saving goal.
#####2.CAButton Modification:
Add new attribute AllowsSelected (including set, get), default value is false. If value is true then selected mode is started. In this mode, the button status will switch between selected and default: if current status is default one, press button and release and button will switch to selected status; press and release again and button will switch back to default status. However, we can promptly judge and know current button status by using getSelected () method.
#####3.CASchedule (modified based on CCSchedule):
**a)	Simplify usage: ** start, stop and other operation can be acted by calling corresponding static methods.<br/>
**b) Memory management modification: ** previously when we start timer, reference counting on pTarget will plus 1 for the purpose of avoiding program crashed caused by pTarget release. Now the new mechanism we deployed could completely solve the above problem: this mechanism enables system to stop plus 1 on pTarget reference counting after timer starts and auto-stop related timer after pTarget release.<br/>
**c) New feature: ** when pTarget is derived type of CAViewController or others and the view of CAViewController is not displayed, timer will auto pause; it will auto recover when the view is displayed. In addition, it will not change before pTarget releases.<br/>
#####4.Demo Location Change
Demo location is moved to samples folder from projects.



####Contributors: juguanhui，


####【9秒实验室自研】

 CrossApp引擎详细介绍请点此：

<http://www.9miao.com/crossapp/list-24-1.html>

#### 9miao Lab Original

 Click to view CrossApp engine detailed description:<br/>
<http://www.9miao.com/crossapp/list-24-1.html>
 
####近期要做的控件：  
提示框：CAAlertView  
视图分页控制器：CAPageControl  
大段文字输入控件：CATextView  
加载状态控制器：CAActivity  
网页加载控件：CAWebView  

####Recent Controls Writing Plan:
Reminder: CAAlertView<br/>
View paging controller: CAPageControl<br/>
Blocks of text input control: CATextView<br/>
Load status controller: CAActivity<br/>
Webpage load control: CAWebView<br/>

####目前已有控件缺失功能：  
#####1.	textField：  	
**a)	**文字内容长度自由限制  
**b)	**光标移动到文字内容任意位置编辑功能  
**c)	**换行输入功能
#####2.	tableView：
**a)	**cell的复用  
**b)	**cell编辑功能（删除、插入等）  
**c)	**cell的一些模板属性的添加  

####Existed Controls Missing Features:
#####1.	textField：
**a) ** free control on text content length<br/>
**b) ** text editing when cursor is at anywhere of content<br/>
**c) **line feed input<br/>
#####2.	tableView：
**a) **cell Multiplexing<br/>
**b) **cell editing (delete, insert and others)<br/>
**c) **add some template attributes of cell<br/>

# 简单易上手的Android MVVM 架构
### 1.MVVM架构简单介绍
MVVM架构图如下：

![image](https://user-images.githubusercontent.com/13979829/132184570-a92da25b-4c11-4a5d-a081-38dfb58a18db.png)

说明：
- M：Model（服务器上的业务逻辑操作，数据库和网络访问）
- V：View（页面 xml和activity）
- VM：ViewModel（Model与View之间核心枢纽，业务逻辑）



### 2.如何引用

##### Project build.gradle配置

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

##### Module build.gradel配置

```groovy
dependencies {
        implementation 'com.github.zhang-hai:CommonMVVMArch:1.0.3'
}
```


### 3.使用

先看下目录结构：

![image](https://user-images.githubusercontent.com/13979829/132186669-f7a0add5-0ee9-4c55-8b5f-ee49c8d4926d.png)

##### 3.1 ViewModel和Model
用户自定义的ViewModel类需继承自BaseViewModel，并设置与之对应的Model类；
如：
```groovy
  public class LoginViewModel extends BaseViewModel<LoginModel> {
    ...
  }
```

BaseViewModel提供`postValue(异步更新)`、`setValue(主线程同步更新)`，直接通过设置一个key就可以自动创建需要的MutableLiveData对象,后续均可直接使用该key进行取值

如：` postValue(key,value)`

showLoading 显示loading弹窗（异步方式，支持在非主线程使用）

hideLoading 隐藏loading弹窗(异步方式，支持在非主线程使用)

postToast   显示Toast消息（异步方式，支持在非主线程使用）

自定义的Model类继承值BaseModel

##### 3.2 BaseActivity和BaseFragment

- 默认注册了loading和toast的Obersve

- 支持Activity和Fragment使用同一个ViewModel实现数据交互和数据共享，

	使用方式：**重写useSameViewModelOfActivity()方法，并返回true**

	`特别注：`此时设置的Activity的ViewModel必须要与Fragment设置的ViewModel是同一个类。



##### 3.3 针对未使用databinding
请继承BaseActivity或BaseFragment，这两个基类仅需设置ViewModel类
子类可直接使用变量`mViewModel`获取对应的ViewModel实例

如：
```groovy
  public class LoginActivity extends BaseActivity<LoginViewModel> {
    ...
  }
```



##### 3.4 针对使用databinding
请继承BaseWithViewBindingActivity或BaseWithViewBindingFragment类，
这两个基类增加了ViewDataBinding泛型，设置后，可在子类中直接使用变量`mViewBinding`获取对应资源文件的ViewBinding对象。

其中，

```groovy
  protected abstract int getLayoutId()
```
是设置activity或fragment对应的布局文件

如：
```groovy
  public class MainResFragment extends BaseWithViewBindingFragment<MainResViewModel, FragmentMainResBinding> {
    ...
  }
```


详情请参考demo


#####版本更新记录

**V1.0.3**

1.BaseFragment中新增` useSameViewModelOfActivity() `方法，支持Activity和Fragment使用同一个ViewModel实现数据共享。


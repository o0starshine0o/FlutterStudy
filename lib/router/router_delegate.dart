import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/router/router_path.dart';

class MyRouterDelegate extends RouterDelegate<RouterPath> with ChangeNotifier, PopNavigatorRouterDelegateMixin<RouterPath> {
  // 静态实例,跟随类加载创建
  static final _singleton = MyRouterDelegate._internal();

  // 通过factory实现单例,调用new方法也只会返回这个单例
  factory MyRouterDelegate() => _singleton;

  // 初始化的
  MyRouterDelegate._internal();

  // 保存所有页面,类似于activity栈
  List<Page> _pages = [];

  void push(String destination) {
    /// 先修改路由表,再往_pages里面添加页面
    _pages.add(MaterialPage(key: ValueKey(destination), child: RouterPath().push(destination)));

    /// 通知MyRouterDelegate调用build()来完成页面的更新
    notifyListeners();
  }

  // PopNavigatorRouterDelegateMixin需要,会把这个navigatorKey赋值给Navigator,所有Navigator不需要再额外提供key参数了
  @override
  GlobalKey<NavigatorState> get navigatorKey => GlobalKey<NavigatorState>();

  /// Step2:
  /// RouterDelegate’s setNewRoutePath method is called with this data type,
  /// and must update the application state to reflect the change
  /// (for example, by setting the selectedBookId) and call notifyListeners.
  /// When a new route has been pushed to the application,
  /// Router calls setNewRoutePath, which gives our app the opportunity to
  /// update the app state based on the changes to the route:
  @override
  Future<void> setNewRoutePath(RouterPath path) async {
    /// Note that the key for the page is defined by the value of the book object.
    /// This tells the Navigator that this MaterialPage object is different from another when the Book object is different.
    /// Without a unique key, the framework can’t determine when to show a transition animation between different Pages.
    // BottomNavigation 作为最底层Widget,每次都需要
    _pages.clear();
    // 其他页面个根据uri来进行管理
    path.uri.pathSegments.forEach((page) {
      _pages.add(MaterialPage(key: ValueKey(page), child: path.map[page]));
    });
  }

  /// 响应返回按钮
  @override
  Future<bool> popRoute() async => RouterPath().pop();

  /// Step3:
  /// When notifyListeners is called, it tells the Router to rebuild the RouterDelegate (using its build() method)
  /// RouterDelegate.build() returns a new Navigator, whose pages now reflect the change to the app state (for example, the selectedBookId).
  /// 调用notifyListeners()时,会重新执行到build方法
  @override
  Widget build(BuildContext context) => Navigator(
      pages: List.of(_pages),

      /// This function is called whenever Navigator.pop() is called.
      /// It should be used to update the state (that determines the list of pages),
      /// and it must call didPop on the route to determine if the pop succeeded
      onPopPage: (route, result) {
        /// It’s important to check whether didPop fails before updating the app state.
        /// 如果还没有关闭成功过,那就返回false
        // if (!route.didPop(result)) {
        //   return false;
        // }

        // RouterPath().pop();

        /// The onPopPage callback now uses notifyListeners instead of setState, since this class is now a ChangeNotifier, not a widget
        /// 关闭成功了,需要更新路由表,并且移除_pages中对应的Page
        notifyListeners();
        return true;
      });
}

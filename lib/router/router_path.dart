import 'package:flutter/material.dart';
import 'package:flutter_app/home/home.dart';

import 'router_map.dart';

/// Instead, you might choose to use different classes that implement a superclass, or manage the route information in another way
/// 这个类后期可以放到外头去,通过接口等形式完成
class RouterPath {
  // 静态实例,跟随类加载创建
  static final _singleton = RouterPath._internal();

  // 保存所有的路由界面
  Map<String, Widget> map = RouterMap.map;

  // 保存当前的路由路径
  Uri _uri = Uri.parse('/');

  // 初始化的,默认第一个页面的uri为'/'
  RouterPath._internal();

  // 通过factory实现单例,调用new方法也只会返回这个单例
  factory RouterPath() => _singleton;

  /// 兼容Navigation1.0,通过push的方式添加一个页面
  Widget push(Type destination) {
    uriString('${_uri.path}/$destination');
    return map['$destination'];
  }

// 弹出最后一个
  bool pop() {
    uriString('/${(List.of(_uri.pathSegments)..removeLast()).join('/')}');
    return true;
  }

  // 获取当前的栈
  Uri get uri => _uri;

  // 完全自定义栈
  uriString(String uri) {
    // 默认进来就是空的,这里需要做一个转换
    _uri = Uri.parse(uri == '/' ? '/$BottomNavigation' : uri);
  }
}

import 'package:flutter/material.dart';

import 'router_path.dart';

class MyRouteInformationParser extends RouteInformationParser<RouterPath> {
  /// Step1:
  /// When the platform emits a new route (for example, “/suggestion”) ,
  /// the RouteInformationParser converts it into an abstract data type T
  /// that you define in your app (for example, a class called RoutePath).
  /// 主要就是把string转换成RouterPath
  /// 默认进来的第一个页面routeInformation.location为'/'
  @override
  Future<RouterPath> parseRouteInformation(RouteInformation routeInformation) async => RouterPath()..uriString(routeInformation.location);

  /// 把RouterPath在转换成string
  @override
  RouteInformation restoreRouteInformation(RouterPath path) => RouteInformation(location: path.uri.path);
}

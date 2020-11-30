// Copyright 2018 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.
import 'package:flutter/material.dart';
import 'package:flutter_app/demo/saved_words.dart';
import 'package:flutter_app/router/router_delegate.dart';
import 'package:flutter_app/router/router_parser.dart';
import 'package:provider/provider.dart';

void main() => runApp(MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) => SavedWords(),
        )
      ],
      child: MyApp(),
    ));

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AppState();
}

class _AppState extends State<MyApp> {
  var _informationParser = MyRouteInformationParser();
  var _routerDelegate = MyRouterDelegate();

  @override
  Widget build(BuildContext context) => MaterialApp.router(routeInformationParser: _informationParser, routerDelegate: _routerDelegate);
}

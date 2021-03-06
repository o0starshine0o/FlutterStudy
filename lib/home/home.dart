// Copyright 2019 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:animations/animations.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/chat/chat_hybrid.dart';
import 'package:flutter_app/chat/chat_virtual.dart';
import 'package:flutter_app/pigeons/messages.dart';
import 'package:flutter_app/words/words.dart';

enum BottomNavigationDemoType {
  withLabels,
  withoutLabels,
}

class BottomNavigation extends StatefulWidget {
  const BottomNavigation(this.type, {Key key }) : super(key: key);

  final BottomNavigationDemoType type;

  @override
  _BottomNavigationState createState() => _BottomNavigationState();
}

class _BottomNavigationState extends State<BottomNavigation> {
  int _currentIndex = 0;

  String _title(BuildContext context) {
    return '标题';
  }

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;
    final textTheme = Theme.of(context).textTheme;

    var bottomNavigationBarItems = <BottomNavigationBarItem>[
      BottomNavigationBarItem(
        icon: Icon(Icons.add_comment),
        label: 'Words',
      ),
      BottomNavigationBarItem(
        icon: Icon(Icons.calendar_today),
        label: 'Virtual',
      ),
      BottomNavigationBarItem(
        icon: Icon(Icons.account_circle),
        label: 'Hybrid',
      ),
      BottomNavigationBarItem(
        icon: Icon(Icons.alarm_on),
        label: '闹钟',
      ),
      BottomNavigationBarItem(
        icon: Icon(Icons.camera_enhance),
        label: '相机',
      ),
    ];

    if (widget.type == BottomNavigationDemoType.withLabels) {
      bottomNavigationBarItems = bottomNavigationBarItems.sublist(0, bottomNavigationBarItems.length - 2);
      _currentIndex = _currentIndex.clamp(0, bottomNavigationBarItems.length - 1).toInt();
    }

    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Center(
            child: TextButton(
          style: TextButton.styleFrom(primary: Colors.white),
          child: Text(_title(context)),
          onPressed: _startIMActivity,
        )),
      ),
      body: Center(
        child: PageTransitionSwitcher(
          child: _NavigationDestinationView(
            // Adding [UniqueKey] to make sure the widget rebuilds when transitioning.
            key: UniqueKey(),
            item: bottomNavigationBarItems[_currentIndex],
          ),
          transitionBuilder: (child, animation, secondaryAnimation) => FadeThroughTransition(
            child: child,
            animation: animation,
            secondaryAnimation: secondaryAnimation,
          ),
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        showUnselectedLabels: widget.type == BottomNavigationDemoType.withLabels,
        items: bottomNavigationBarItems,
        currentIndex: _currentIndex,
        type: BottomNavigationBarType.fixed,
        selectedFontSize: textTheme.caption.fontSize,
        unselectedFontSize: textTheme.caption.fontSize,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        selectedItemColor: colorScheme.onPrimary,
        unselectedItemColor: colorScheme.onPrimary.withOpacity(0.38),
        backgroundColor: colorScheme.primary,
      ),
    );
  }

  Future<void> _startIMActivity() async {
    try {
      await Api().startIMActivity();
    } on Exception {}
  }
}

class _NavigationDestinationView extends StatelessWidget {
  _NavigationDestinationView({Key key, this.item}) : super(key: key);

  final BottomNavigationBarItem item;

  @override
  Widget build(BuildContext context) {
    switch (item.label) {
      case 'Words':
        return RandomWords();
      case 'Virtual':
        return ChatLayoutVirtual();
      case 'Hybrid':
        return ChatLayoutHybrid();
      default:
        return Center(
          child: Text(item.label),
        );
    }
  }
}

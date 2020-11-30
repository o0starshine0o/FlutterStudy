import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_app/global/global.dart';
import 'package:flutter_app/pigeons/messages.dart';
import 'package:flutter_app/router/router_delegate.dart';

class RandomWords extends StatefulWidget {
  @override
  _RandomWordsState createState() => _RandomWordsState();
}

class _RandomWordsState extends State<RandomWords> {
  final _suggestion = <WordPair>[];
  final _biggerFont = TextStyle(fontSize: 18);
  final _batteryChannel = MethodChannel('com.example.flutter_app/battery');
  int _batteryLevel = 0;
  String _replay = '';

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: TextButton(
            style: TextButton.styleFrom(primary: Colors.white),
            child: Text('$_replay: $_batteryLevel'),
            onPressed: _getBattery,
          ),
          actions: [IconButton(icon: Icon(Icons.list), onPressed: _pushSaved)],
        ),
        body: _buildSuggestions(),
      );

  Widget _buildSuggestions() => ListView.builder(
        padding: EdgeInsets.all(16),
        itemBuilder: (context, i) {
          if (i >= _suggestion.length) _suggestion.addAll(generateWordPairs().take(10));
          return _buildRow(_suggestion[i]);
        },
      );

  Widget _buildRow(WordPair pair) {
    var alreadySaved = Global.saved.contains(pair);
    return Column(
      children: [
        ListTile(
          title: Text(
            pair.asPascalCase,
            style: _biggerFont,
          ),
          trailing: Icon(
            alreadySaved ? Icons.favorite : Icons.favorite_border,
            color: alreadySaved ? Colors.red : null,
          ),
          onTap: () => _triggerSaved(pair, alreadySaved),
        ),
        Divider(),
      ],
    );
  }

  void _pushSaved() {
    MyRouterDelegate().push('suggestion');
  }

  void _triggerSaved(WordPair pair, bool alreadySaved) => setState(() {
        alreadySaved ? Global.saved.remove(pair) : Global.saved.add(pair);
      });

  Future<void> _getBattery() async {
    try {
      int result = await _batteryChannel.invokeMethod('getBattery');
      SearchReplay replay = await Api().search(SearchRequest()..query = 'query');
      setState(() {
        _batteryLevel = result;
        _replay = replay.result;
      });
    } on PlatformException {
      setState(() {
        _batteryLevel = -1;
        _replay = '';
      });
    }
  }
}

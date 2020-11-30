import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_app/demo/saved_words.dart';
import 'package:flutter_app/pigeons/messages.dart';
import 'package:flutter_app/router/router_delegate.dart';
import 'package:provider/provider.dart';

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
    return Consumer<SavedWords>(
      builder: (context, words, child) => Column(
        children: [
          ListTile(
            title: Text(
              pair.asPascalCase,
              style: _biggerFont,
            ),
            trailing: Icon(
              words.contains(pair) ? Icons.favorite : Icons.favorite_border,
              color: words.contains(pair) ? Colors.red : null,
            ),
            onTap: () => Provider.of<SavedWords>(context).trigger(pair),
          ),
          Divider(),
        ],
      ),
    );
  }

  void _pushSaved() {
    MyRouterDelegate().push('suggestion');
  }

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

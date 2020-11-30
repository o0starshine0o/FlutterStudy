import 'dart:collection';

import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

class SavedWords extends ChangeNotifier {
  final List<WordPair> _words = [];

  UnmodifiableListView<WordPair> get words => UnmodifiableListView(_words);

  trigger(WordPair wordPair) {
    if (_words.contains(wordPair)) {
      remove(wordPair);
    } else {
      add(wordPair);
    }
  }

  contains(WordPair pair) => _words.contains(pair);

  add(WordPair wordPair) {
    _words.add(wordPair);
    notifyListeners();
  }

  remove(WordPair wordPair) {
    _words.remove(wordPair);
    notifyListeners();
  }
}

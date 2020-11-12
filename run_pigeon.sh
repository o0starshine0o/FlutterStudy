flutter pub run pigeon \
  --input pigeons/message.dart \
  --dart_out lib/pigeons/messages.dart \
  --objc_header_out ios/Classes/messages.h \
  --objc_source_out ios/Classes/messages.m \
  --objc_prefix FLT \
  --java_out android/app/src/main/java/com/example/pigeon_plugin/Messages.java \
  --java_package "com.example.pigeon_plugin"
# SWAPI APP

## 開発環境
[![AndroidStudio](https://img.shields.io/badge/Android%20Studio-Dolphin%20%7C%202021.3.1-blue)](https://developer.android.com/studio/)

## アプリ概要
スターウォーズのAPIを使用したアプリ

API:https://swapi.dev/

スターウォーズの、登場人物、映画、人種、宇宙船、自動車、惑星を紹介する

* 最小API Level:28
* ターゲットAPI Level:31

## ドキュメント
* https://swapi.dev/documentation

## 使用ライブラリ
|ライブラリ名|バージョン|用途|
|--|--|--|
| kotlin-serialization | 1.3.1 | jsonコンバーター |
| Retrofit | 2.9.0 | 通信ライブラリ |
| Timber | 4.7.1 | ログ出力 |
| | | |
| | | |
| | | |



## パッケージ構成
| パッケージ名 | 説明 |
|--|--|
| activity | Activity |
| adapter | RecyclerView の Adapter |
| api | API処理の基盤 |
| base | BaseFragment、BaseViewModel |
| data | データクラス、データベースのレスポンスなど |
| fragment | Fragment 各FragmentのViewModel |
| repository | APIのリクエスト、データベースの操作などを行う |
| utils | enum、WebViewのリンク、Date型の処理など |

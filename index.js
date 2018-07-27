import { AppRegistry } from 'react-native';
import App from './App';
import UserInfoScreen from './src/screen/UserInfoScreen';

import {StackNavigator} from "react-navigation";

import HeadlessTest from './src/service/HeadlessTest';

const AppHome = StackNavigator({
    App: {screen: App},
    UserInfoScreen: {screen: UserInfoScreen},
});

AppRegistry.registerComponent('FirstApp', () => AppHome);
AppRegistry.registerHeadlessTask("HeadlessTest",()=>HeadlessTest);

import { AppRegistry,Image } from 'react-native';
import App from './App';
import UserInfoScreen from './src/screen/UserInfoScreen';

import {StackNavigator} from "react-navigation";



const AppHome = StackNavigator({
    App: {
        screen: App,

    },
    UserInfoScreen: {screen: UserInfoScreen},
});

AppRegistry.registerComponent('FirstApp', () => AppHome);




import React, { Component } from 'react';
import PermissionScreen from './src/screen/PermissionScreen'
import UserInfoScreen from './src/screen/UserInfoScreen'
import {
    StackNavigator,
} from 'react-navigation'
export default class Pages extends Component{
    constructor(props){
        super(props);
    }
    render(){
        return(
            <SimpleAppNavigator/>
        );
    }
}
const SimpleAppNavigator = StackNavigator({
    PermissionScreen: {screen: PermissionScreen},
    UserInfoScreen: {screen: UserInfoScreen},
},{
    initialRouteName: 'PermissionScreen',
});
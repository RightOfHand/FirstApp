import React, { Component } from 'react';
import {
    StyleSheet,
    Alert,
    View,
    BackHandler,
    ToastAndroid,
    Platform,
    Text
} from 'react-native';

type Props = {};
export default class DetailScreen extends Component<Props>{
    static navigationOptions = ({navigation}) => ({
        // 展示数据 "`" 不是单引号
        title: `${navigation.state.params.user}`,

    });
    lastBackPressed = 0;
    componentDidMount() {
        if (Platform.OS === 'android') {
            BackHandler.addEventListener('hardwareBackPress', this.onBackAndroid);
        }
    }
    componentWillUnmount() {
        if (Platform.OS === 'android') {
            BackHandler.removeEventListener('hardwareBackPress', this.onBackAndroid);
        }
    }
    onBackAndroid = () => {
        const {navigator}=this.props;
        if (navigator!=null){
            Alert.alert("how much"+navigator.getLength())
        }
        if (navigator!=null && navigator.getLength()>1){
            navigator.pop();
            return true;
        }else {
            return false;
        }
        const now = Date.now();
        if (now - this.lastBackPressed < 1500) {
            BackHandler.exitApp();
        } else {
            this.lastBackPressed = now;
            ToastAndroid.show('再按一次返回键退出应用', 1000);
        }

        return true;

    };
    constructor(props){
        super(props);
    }

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View style={styles.container}>

                <Text style={styles.text}>i am from native </Text>

            </View>
        );
    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        marginTop:20, //去除状态栏图标
        backgroundColor: 'gray',
    },
    rowContainer: {
        backgroundColor: '#FFFFFF',
    },
    text: {
        textAlign: 'center',
        fontWeight: 'bold',
    },
});

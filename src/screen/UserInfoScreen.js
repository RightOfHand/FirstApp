import React, { Component } from 'react';
import {
    StyleSheet,
    Alert,
    View,
    BackHandler,
    ToastAndroid,
    Platform,
    Text,
    AsyncStorage,
    PixelRatio,
    processColor,
} from 'react-native';


import TextInputWidget from '../widget/TextInputWidget.js';
import TextTipsWidget from '../widget/TextTipsWidget';
import TextImageWidget from '../widget/TextImageWidget.js';
import Circle from '../widget/NativeCircle'




type Props = {};
export default class UserInfoScreen extends Component<Props>{
    static navigationOptions = ({navigation}) => ({
        // 展示数据 "`" 不是单引号
        title: `${navigation.state.params.user}`,

    });
    lastBackPressed = 0;
    componentWillMount() {
        if (Platform.OS === 'android') {
            // BackHandler.addEventListener('hardwareBackPress', this.onBackAndroid);
        }
    }
    componentWillUnmount() {
        if (Platform.OS === 'android') {
            // BackHandler.removeEventListener('hardwareBackPress', this.onBackAndroid);
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

                <TextImageWidget
                    title='* 头像' />

                <TextImageWidget
                    title='* 头像2' />
                <TextInputWidget
                    title='* 昵称'
                    placeholder='请输使用您的真实姓名' />
                <TextTipsWidget title='* 性别' tips='请选择'/>
                <TextTipsWidget
                    title='* 生日' tips='请选择'/>
                <View style={styles.rowContainer}>
                    <Circle style={{width: 100, height: 100}}
                            color={processColor("#f45675")}
                            radius={50}
                            onClick={(msg)=>Alert.alert("js press",msg)}/>
                </View>
                <View>
                    {/*<Text  style={styles.textTitle} onPress={()=>this.onSendCommand()}>*/}
                        {/*send task*/}
                    {/*</Text>*/}
                </View>

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
    }
});

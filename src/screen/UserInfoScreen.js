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
    UIManager,
    findNodeHandle
} from 'react-native';


import TextInputWidget from '../widget/TextInputWidget.js';
import TextTipsWidget from '../widget/TextTipsWidget';
import TextImageWidget from '../widget/TextImageWidget.js';
import Circle from '../widget/NativeCircle'
import UpdateAndroid from '../native_modules/UpdateAndroid'




type Props = {};
export default class UserInfoScreen extends Component<Props>{
    // static navigationOptions = ({navigation}) => ({
    //     // 展示数据 "`" 不是单引号
    //     title: `${navigation.state.params.user}`,
    //
    // });
    lastBackPressed = 0;
    componentDidMount() {
    //     if (Platform.OS === 'android') {
    //         // BackHandler.addEventListener('hardwareBackPress', this.onBackAndroid);
    //     }
    // }
    // componentWillUnmount() {
    //     if (Platform.OS === 'android') {
    //         // BackHandler.removeEventListener('hardwareBackPress', this.onBackAndroid);
    //     }
    }
    // onBackAndroid = () => {
    //     const {navigator}=this.props;
    //     if (navigator!=null){
    //         Alert.alert("how much"+navigator.getLength())
    //     }
    //     if (navigator!=null && navigator.getLength()>1){
    //         navigator.pop();
    //         return true;
    //     }else {
    //         return false;
    //     }
    //     const now = Date.now();
    //     if (now - this.lastBackPressed < 1500) {
    //         BackHandler.exitApp();
    //     } else {
    //         this.lastBackPressed = now;
    //         ToastAndroid.show('再按一次返回键退出应用', 1000);
    //     }
    //
    //     return true;
    //
    // };
    constructor(props){
        super(props);
    }

    _update(){
        UpdateAndroid.doUpdate('index.android.bundle_2.0', (progress)=> {
            let pro = Number.parseFloat('' + progress);
            if (pro >= 100) {
                this.setState({
                    modalVisible: false,
                    currProgress: 100
                });
            } else {
                this.setState({
                    currProgress: pro
                });
            }
        });
    }
    _onSend(){
        this.circle.handleTask();
    }
    render() {
        // const { navigate } = this.props.navigation;
        return (
            <View style={styles.container}>
                <TextImageWidget
                    title='* 头像' />
                <TextInputWidget
                    title='* 昵称'
                    placeholder='请输使用您的真实姓名' />
                <TextTipsWidget title='* 性别' tips='请选择'/>
                <TextTipsWidget
                    title='* 生日' tips='请选择'/>
                <View style={styles.rowContainer}>

                    <Circle ref={(circle)=>{this.circle = circle}}
                            style={{width: 100, height: 100}}
                            color={processColor("#f45675")}
                            radius={50}
                            onClick={(msg)=>Alert.alert("js press",msg)}/>
                </View>
                <View>
                    <Text  style={styles.rowContainer} onPress={()=>this._onSend()}>
                        send task
                    </Text>
                </View>
                <View>
                    <Text  style={styles.rowContainer} onPress={()=>this._update()}>
                        send task
                    </Text>
                </View>
            </View>
        );
    }

}

const styles = StyleSheet.create({
    rowContainer: {
        backgroundColor: '#FFF',
        width:200,
        fontSize: 18,
    },
    container: {
        flex: 1,
        marginTop:20, //去除状态栏图标
        backgroundColor: 'gray',
    },
    rowContainer: {
        backgroundColor: '#FFFFFF',
    }
});

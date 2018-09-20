import React, { Component } from 'react';
import {
    StyleSheet,
    View,
    Text,
    Image,
    DeviceEventEmitter,
    Alert,
    PixelRatio,
    PermissionsAndroid,
    Dimensions
} from 'react-native'

//从android.js中导入
import ImagePickerModule from '../native_modules/NativeCamera'
var screenWidth = Dimensions.get('window').width;


export default class TextImageWidget extends Component{

    constructor(props) {
        super(props);
    }
    // componentWillMount(){
    //     DeviceEventEmitter.addListener('AndroidToRNMessage',this.handleAndroidMessage);
    // }
    //
    // componentWillunMount(){
    //     DeviceEventEmitter.remove('AndroidToRNMessage',this.handleAndroidMessage);
    // }
    // handleAndroidMessage=(msg)=>{
    //     //RN端获得native端传递的数据
    //     Alert.alert(msg)
    //     console.log(msg);
    // }

    promiseNative(msg) {
        ImagePickerModule.pickImage(msg).then(
            (result) =>{
                Alert.alert(result)
            }
        ).catch((error) =>{console.log(error)});
    }
    _renderRow() {
        return (
            <View style={styles.rowContainer}>
                <View style={styles.row}>
                    <Text
                        style={styles.textInputTitle} onPress={()=>this.promiseNative("原生跳用")}>
                        {this.props.title}
                    </Text>
                    <View style={styles.textImage}>
                        <Image source={require('../../img/demo.gif')} style={styles.image}/>
                    </View>
                    <View style={styles.rightArrow}>
                        <Image source={require('../../img/ic_arrow_gray.png')}  />
                    </View>
                </View>
            </View>
        );

    }

    render() {
        return this._renderRow();
    }
}

const styles = StyleSheet.create({
    rowContainer: {
        backgroundColor: '#FFF',
        width:screenWidth,
    },
    row: {
        flexDirection: 'row',
        height: 44,
        alignItems: 'center',
        marginRight: 15,
        marginLeft: 15,
        //paddingTop:15,
        borderBottomWidth: 0.5 / PixelRatio.get(),
        borderColor:'gray',//需要标色

    },
    textInputTitle: {
        width: 80,
        fontSize: 13,
    },
    textImage: {
        flex: 1,
        height: 44,// @todo should be changed if underlined
        justifyContent:'flex-end',
        flexDirection: 'row',

    },
    image:{
        width:38,
        height:38,
        backgroundColor:'gray',
        borderRadius: 19,
    },
    rightArrow:{
        flex: 1,
        justifyContent:'flex-end',
        flexDirection: 'row',
        //backgroundColor:'red',

    }

});

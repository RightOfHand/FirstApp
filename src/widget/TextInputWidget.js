import React, { Component } from 'react';
import {
    StyleSheet,
    View,
    Text,
    TextInput,
    PixelRatio,
    Dimensions
} from  'react-native'

import NativePageModule from '../native_modules/NativeScreen';

var screenWidth = Dimensions.get('window').width;


export default class TextImageWidget extends Component{

    constructor(props) {
        super(props);
    }

    _renderRow() {
        return (
            <View style={styles.rowContainer}  >
                <View style={styles.row}>
                    <Text
                        style={styles.textTitle} onPress={()=>NativePageModule.toNative("I am from RN")} >
                        {this.props.title}
                    </Text>
                    <View style={styles.row}>
                        <TextInput
                            numberOfLines={1}
                            style={styles.textInputTitle}
                            placeholder={this.props.placeholder}
                            underlineColorAndroid="transparent">
                        </TextInput>
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
        backgroundColor: '#FFFFFF',
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
    textTitle: {
        width: 80,
        fontSize: 13,
    },
    textInputTitle: {
        fontSize: 13,
        width:100,
        height: 40,
    },
    rightArrow:{
        paddingLeft:10,
        //backgroundColor:'red',

    }

});

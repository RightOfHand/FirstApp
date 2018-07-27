import React, { Component } from 'react';
import {
    StyleSheet,
    View,
    Text,
    Image,
    PixelRatio,
    Dimensions
} from 'react-native'


var screenWidth = Dimensions.get('window').width;


export default class TextImageWidget extends Component{

    constructor(props) {
        super(props);
    }

    _renderRow() {
        return (
            <View style={styles.rowContainer}>
                <View style={styles.row}>
                    <Text
                        numberOfLines={1}
                        style={styles.textTitle} >
                        {this.props.title}
                    </Text>
                    <View style={styles.row}>
                        <Text
                            numberOfLines={1}
                            style={styles.textInputTitle} onPress={()=>{
                        }}>
                            {this.props.tips}
                        </Text>
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

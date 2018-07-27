import React, { Component } from 'react';
import {
    View,
    requireNativeComponent,
} from 'react-native';
import {PropTypes} from 'prop-types'

var iFace={
    name:'Circle',
    propTypes:{
        color:PropTypes.number,
        radius:PropTypes.number,
        ...View.propTypes // 包含默认的View的属性
    },
    nativeOnly:{
        onclick:true
    }
}
module.exports=requireNativeComponent('MCircle', iFace);
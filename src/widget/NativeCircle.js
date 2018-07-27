import React, { Component } from 'react';
import {
    View,
    UIManager,
    findNodeHandle
} from 'react-native';

import {PropTypes} from 'prop-types'
import MCircle from '../native_modules/Circle'

var RCT_CICLE_REF='MCircle'
export default class Circle extends Component {

    static propTypes = {
        radius: PropTypes.number,
        color: PropTypes.number, // 这里传过来的是string
        ...View.propTypes // 包含默认的View的属性
    }

    onClick(event) {
        if(this.props.onClick) {
            if(!this.props.onClick){
                return;
            }
            // 使用event.nativeEvent.msg获取原生层传递的数据
            this.props.onClick(event.nativeEvent.msg);
        }
    }


    handleTask() {
        //向native层发送命令
        // noinspection JSDeprecatedSymbols
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(this.refs[RCT_CICLE_REF]),
            UIManager.MCircle.Commands.handleTask,
            null)
    }

    render() {
        const { style, radius, color } = this.props;

        return (
            <View>
                <MCircle
                    ref={RCT_CICLE_REF}
                    style={style}
                    radius={radius}
                    color={color}
                    onClick={(event)=> this.onClick(event)}
                />
            </View>

        );
    }

}

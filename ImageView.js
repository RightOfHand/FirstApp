import PropTypes from 'prop-types'
import {requireNativeComponent, ViewPropTypes} from 'react-native';

var iface = {
    name: 'RCTImageView',
    propTypes: {
        borderColor: PropTypes.number,
        borderWidth: PropTypes.number,
    },
};
module.exports=requireNativeComponent('RCTImageView',iface)

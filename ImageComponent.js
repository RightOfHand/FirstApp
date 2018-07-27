import React from "react";
import RCTImageView from './ImageView'

class RCTMyCustomView extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <RCTImageView {this.props.borderWidth}/>;
    }

}
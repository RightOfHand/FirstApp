// @flow

import React, { Component } from 'react'
import {
    StyleSheet,
    TouchableHighlight,
    Text,
    View,
    Alert,
    AppState,
    Platform,
} from 'react-native'

import ReactNativePermissions from './src/util/android.permission.js'
import UserInfoScreen from "./src/screen/UserInfoScreen";
export default class App extends Component {
    static navigationOptions = {
        title: 'choose permission',
        headerText:{
            textAlign: 'center',
            fontWeight: 'bold',
        },
        gesturesEnabled:false
    };
    state = {
        types: [],
        status: {},
    }

    componentDidMount() {
        let types = ReactNativePermissions.getTypes()
        let canOpenSettings = ReactNativePermissions.canOpenSettings()

        this.setState({ types, canOpenSettings })
        this._updatePermissions(types)
        AppState.addEventListener('change', this._handleAppStateChange)
    }

    componentWillUnmount() {
        AppState.removeEventListener('change', this._handleAppStateChange)
    }

    //update permissions when app comes back from settings
    _handleAppStateChange = appState => {
        if (appState == 'active') {
            this._updatePermissions(this.state.types)
        }
    }

    _openSettings = () =>
        ReactNativePermissions.openSettings().then(() => alert('back to app!!'))

    _updatePermissions = types => {
        ReactNativePermissions.checkMultiple(types)
            .then(status => {
                if (this.state.isAlways) {
                    return ReactNativePermissions.check('location', 'always').then(location => ({
                        ...status,
                        location,
                    }))
                }
                return status
            })
            .then(status => this.setState({ status }))
    }

    _requestPermission = permission => {
        var options

        if (permission == 'location') {
            options = this.state.isAlways ? 'always' : 'whenInUse'
        }

        ReactNativePermissions.request(permission, options)
            .then(res => {
                this.setState({
                    status: { ...this.state.status, [permission]: res },
                })
                if (res != 'authorized') {
                    var buttons = [{ text: 'Cancel', style: 'cancel' }]
                    if (this.state.canOpenSettings)
                        buttons.push({
                            text: 'Open Settings',
                            onPress: this._openSettings,
                        })

                    Alert.alert(
                        'Whoops!',
                        'There was a problem getting your permission. Please enable it from settings.',
                        buttons,
                    )
                }
            })
            .catch(e => console.warn(e))
    }

    _onLocationSwitchChange = () => {
        this.setState({ isAlways: !this.state.isAlways })
        this._updatePermissions(this.state.types)
    }

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View style={styles.container}>
                {this.state.types.map(p => (
                    <TouchableHighlight
                        style={[styles.button, styles[this.state.status[p]]]}
                        key={p}
                        onPress={() => this._requestPermission(p)}
                    >
                        <View>
                            <Text style={styles.text}>
                                {Platform.OS == 'ios' && p == 'location'
                                    ? `location ${this.state.isAlways ? 'always' : 'whenInUse'}`
                                    : p}
                            </Text>
                            <Text style={styles.subtext}>{this.state.status[p]}</Text>
                        </View>
                    </TouchableHighlight>
                ))}
                <Text style={styles.text} onPress={()=>navigate('UserInfoScreen',{user:"个人资料"})}>go to</Text>

                <View style={styles.footer}>
                    <TouchableHighlight
                        style={styles['footer_' + Platform.OS]}
                        onPress={this._onLocationSwitchChange}
                    >
                        <Text style={styles.text}>Toggle location type</Text>
                    </TouchableHighlight>

                    {this.state.canOpenSettings && (
                        <TouchableHighlight onPress={this._openSettings}>
                            <Text style={styles.text}>Open settings</Text>
                        </TouchableHighlight>
                    )}
                </View>

            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        backgroundColor: '#F5FCFF',
        padding: 10,
    },
    text: {
        textAlign: 'center',
        fontWeight: 'bold',
    },
    subtext: {
        textAlign: 'center',
    },
    button: {
        margin: 5,
        borderColor: 'black',
        borderWidth: 3,
        overflow: 'hidden',
    },
    buttonInner: {
        flexDirection: 'column',
    },
    undetermined: {
        backgroundColor: '#E0E0E0',
    },
    authorized: {
        backgroundColor: '#C5E1A5',
    },
    denied: {
        backgroundColor: '#ef9a9a',
    },
    restricted: {
        backgroundColor: '#ef9a9a',
    },
    footer: {
        padding: 10,
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    footer_android: {
        height: 0,
        width: 0,
    },
})

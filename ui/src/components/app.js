import { h, Component } from 'preact';
import { Router } from 'preact-router';

import Header from './header';
import Welcome from '../routes/home';
import Profile from '../routes/profile';
import NotFound from '../routes/404';
// import Home from 'async!../routes/home';
// import Profile from 'async!../routes/profile';
import PickCard from './modals/PickCard';
import TakeTurn from './modals/TakeTurn';
import UseCard from './modals/UseCard';
import Play from './modals/Play';
import JoinGame from "./modals/JoinGame";

export default class App extends Component {
	/** Gets fired when the route changes.
	 *	@param {Object} event		"change" event from [preact-router](http://git.io/preact-router)
	 *	@param {string} event.url	The newly routed URL
	 */
	handleRoute = e => {
		this.setState({
			currentUrl: e.url
		});
	};

	render() {
		return (
			<div id="app">
				<Header selectedRoute={this.state.currentUrl} />
				<Router onChange={this.handleRoute}>
					<Welcome path="/" />
					<JoinGame path="/join" />
					<Play path="/play" />
					<PickCard path="/pick-card" />
					<TakeTurn path="/take-turn" />
					<UseCard path="/use-card" />
					<Profile path="/profile/" user="me" />
					<Profile path="/profile/:user" />
					<NotFound default />
				</Router>
			</div>
		);
	}
}

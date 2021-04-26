import { h, Component } from 'preact';
import { Router } from 'preact-router';

import Header from './header';
import Welcome from '../routes/home';
import NotFound from '../routes/404';
import Play from './modals/Play';
import JoinGame from "./modals/JoinGame";
import Winner from './modals/Winner';
import Loser from './modals/Loser';

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
					<Winner path="/winner" />
					<Loser path="/loser" />
					<NotFound default />
				</Router>
			</div>
		);
	}
}

import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { FolderDialog } from './FolderDialog';
import { Thumbnails } from './Thumbnails'
import { slide as Menu } from 'react-burger-menu';


class App extends Component {
  constructor(props) {
    super(props);
    this.thumbnails = React.createRef();
    this.state = {
      menuOpen: true
    };
  }

  handleStateChange (state) {
    this.setState({menuOpen: state.isOpen})  
  }

  showSettings(event) {
    event.preventDefault();
  }

  folderChose(path) {
    console.log(path);
    this.setState({
      menuOpen: false
    });
    this.thumbnails.current.getImages(path);
  }

  render() {
    return (
      <div id="outer-container" className="body">
        <Menu isOpen={this.state.menuOpen} pageWrapId={ "page-wrap" } outerContainerId={ "outer-container" } onStateChange={(state) => this.handleStateChange(state)}>
          <FolderDialog folderChose={this.folderChose.bind(this)}></FolderDialog>
          <a id="about" className="menu-item" href="/about">About</a>
          <a id="contact" className="menu-item" href="/contact">Contact</a>
          <a onClick={this.showSettings} className="menu-item--small" href="">Settings</a>
        </Menu>
        <main id="page-wrap">
          <div className="row">
            <div className="col-4">
              <Thumbnails ref={this.thumbnails}>
              </Thumbnails>
            </div>
            <div className="col-7"></div>
          </div>
        </main>
      </div>
    );
  }
}

export default App;

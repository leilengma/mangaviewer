import React, { Component } from 'react';
import './FolderDialog.css';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import axios from 'axios';
import { Subject } from 'rxjs';
export class FolderDialog extends Component {
    constructor(props) {
        super(props);
        this.pathChanged = new Subject();
        this.state = {
            modal: false,
            path: [],
            folders: []
        };

        this.toggle = this.toggle.bind(this);
    }

    componentDidMount() {
        this.pathChanged.subscribe(newpath => {
            this.setState({
                path: newpath
            }, () => {
                this.updateFolders();
            });
        })
    }

    updateFolders() {
        axios.get('http://localhost:3001/api/ls', { params: { path: this.state.path.join() } }).then(
            resp => this.setState({ folders: resp.data })
        )
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        }, () => {
            if (this.state.modal) {
                this.updateFolders()
            }
        });
    }

    chooseFolder(folderName) {
        let newpath = this.state.path;
        newpath.push(folderName);
        this.pathChanged.next(newpath);
    }

    back2Parent() {
        let newpath = this.state.path;
        newpath.splice(newpath.length - 1, 1);
        this.pathChanged.next(newpath);
    }

    onFolderChosen(folderName) {
        let folders = this.state.path;
        folders.push(folderName);
        this.props.folderChose(folders);
        this.toggle();
    }

    render() {
        return (
            <div>
                <a onClick={this.toggle} color="primary" href="#">open folder</a >
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className} centered="true">
                    <ModalHeader toggle={this.toggle}>System Explorer</ModalHeader>
                    <ModalBody>
                        <Button onClick={() => { this.back2Parent() }}>Back</Button>
                        <ul className="list-group">
                            {
                                this.state.folders.map(folderName => {
                                    return <div className="list-group-item list-group-item-action d-flex">
                                     <div className="flex-grow-1" onClick={() => { this.chooseFolder(folderName) }} key={folderName}> 
                                        {folderName} 
                                    </div>
                                    <Button className="list-button" color="primary" onClick={() => {this.onFolderChosen(folderName)}}> Open </Button> 
                                    </div>
                                })
                            }
                        </ul>
                    </ModalBody>
                    <ModalFooter>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}
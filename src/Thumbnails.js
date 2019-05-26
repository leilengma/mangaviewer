import React, { Component } from 'react';
import './Thumbnails.css';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import axios from 'axios';
import { Subject } from 'rxjs';
export class Thumbnails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            images: [],
        };
    }

    getImages(folder) {
        axios.get('http://localhost:3001/api/imagesName', { params: { path: folder.join() } }).then(
            resp => {
                let images = [];
                for (let imageName of resp.data) {
                    let copy = folder.slice()
                    copy.push(imageName)
                    images.push('http://localhost:3001/api/image?path='+copy.join());
                }
                this.setState({ images: images})
            }
        )
    }

    render() {
        return (
            <ul className="list-group">
                {
                    this.state.images.map(imagePath => {
                        return <img className="list-group-item thumbnail" src={imagePath}></img>
                    })
                }
            </ul>
        );
    }
}
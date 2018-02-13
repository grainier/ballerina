/**
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import React from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';
import PanelDecorator from '../decorators/panel-decorator';
import ImageUtil from '../../../../image-util';
import StatementDropZone from '../../../../../drag-drop/DropZone';
import LifeLine from '../decorators/lifeline';
import Client from '../decorators/client';
import FunctionNodeModel from '../../../../../model/tree/function-node';
import { getComponentForNodeArray } from './../../../../diagram-util';
import TreeUtil from '../../../../../model/tree-util';
import EndpointDecorator from '../decorators/endpoint-decorator';
import ReceiverNode from './receiver-node';

class FunctionNode extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showStructBinding: false,
        };
        this.canDropToPanelBody = this.canDropToPanelBody.bind(this);
        this.onMouseEnter = this.onMouseEnter.bind(this);
        this.onMouseLeave = this.onMouseLeave.bind(this);
    }

    onMouseEnter() {
        if (!TreeUtil.isMainFunction(this.props.model)) {
            this.setState({ showStructBinding: true });
        }
    }

    onMouseLeave() {
        if (_.isEmpty(this.props.model.viewState.overlayContainer)) {
            if (!TreeUtil.isMainFunction(this.props.model)) {
                this.setState({ showStructBinding: false });
            }
        }
    }

    canDropToPanelBody(dragSource) {
        return TreeUtil.isEndpointTypeVariableDef(dragSource)
            || TreeUtil.isWorker(dragSource);
    }

    render() {
        const model = this.props.model;
        const bBox = model.viewState.bBox;
        const name = model.getName().value;
        // change icon for main function
        let icons = 'tool-icons/function';
        if (TreeUtil.isMainFunction(model)) {
            icons = 'tool-icons/main-function';
        }
        const body = this.props.model.getBody();
        const bodyBBox = body.viewState.bBox;
        const blockNode = getComponentForNodeArray(body, this.context.mode);
        const workers = getComponentForNodeArray(this.props.model.workers, this.context.mode);

        const classes = {
            lineClass: 'default-worker-life-line',
            polygonClass: 'default-worker-life-line-polygon',
        };

        const argumentParameters = this.props.model.getParameters();
        const returnParameters = this.props.model.getReturnParameters();

        const connectors = this.props.model.body.statements
            .filter((element) => {
                const typeNode = _.get(element, 'variable.typeNode');
                return typeNode && TreeUtil.isEndpointType(typeNode);
            }).map((statement) => {
                return (
                    <EndpointDecorator
                        model={statement}
                        title={statement.variable.name.value}
                        bBox={statement.viewState.bBox}
                    />);
            });
        const nodeDetails = ({ x, y }) => (
            <ReceiverNode
                x={x}
                y={y}
                model={model}
                showStructBinding={this.state.showStructBinding}
            />
        );
        let receiverType;
        if (model.getReceiver()) {
            receiverType = model.getReceiver().getTypeNode().getTypeName().value + ' ' +
                model.getReceiver().getName().value;
        }
        return (
            <g
                onMouseLeave={this.onMouseLeave}
                onMouseEnter={this.onMouseEnter}
            >
                <PanelDecorator
                    bBox={bBox}
                    model={this.props.model}
                    headerComponent={nodeDetails}
                    icon={icons}
                    dropTarget={this.props.model}
                    canDrop={this.canDropToPanelBody}
                    argumentParams={argumentParameters}
                    returnParams={returnParameters}
                    title={name}
                    receiver={receiverType}
                >
                    <Client
                        title='caller'
                        bBox={this.props.model.viewState.components.client}
                    />
                    { this.props.model.getWorkers().length === 0 &&
                    <g>
                        <StatementDropZone
                            x={bodyBBox.x}
                            y={bodyBBox.y}
                            width={bodyBBox.w}
                            height={bodyBBox.h}
                            baseComponent='rect'
                            dropTarget={body}
                            enableDragBg
                        />
                        <LifeLine
                            title='default'
                            bBox={this.props.model.viewState.components.defaultWorkerLine}
                            classes={classes}
                            icon={ImageUtil.getCodePoint('worker')}
                            iconColor='#025482'
                        />
                        {blockNode}
                    </g>
                }{
                    this.props.model.workers.map((item) => {
                        return (<StatementDropZone
                            x={item.getBody().viewState.bBox.x}
                            y={item.getBody().viewState.bBox.y}
                            width={item.getBody().viewState.bBox.w}
                            height={item.getBody().viewState.bBox.h}
                            baseComponent='rect'
                            dropTarget={item.getBody()}
                            enableDragBg
                        />);
                    })
                }
                    {workers}
                    {connectors}
                </PanelDecorator> </g>);
    }
}

FunctionNode.propTypes = {
    model: PropTypes.instanceOf(FunctionNodeModel).isRequired,
};

FunctionNode.contextTypes = {
    mode: PropTypes.string,
};

export default FunctionNode;

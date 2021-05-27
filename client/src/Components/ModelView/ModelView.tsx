import './ModelView.css';
import {MouseEvent, useRef} from 'react';
import Spinner from '../Spinner'

/**
 * 
 * @param props 
 * @returns 
 */
export default function ModelView(props: any) {

    const formDetails = useRef(null);

    const handleClick = (evt: MouseEvent) => {
        evt.preventDefault();
        //@ts-ignore
        const textInputs = [...formDetails.current].filter(element => (element.type === "text" || element.type === "tel") && element.value.length > 0);
        let results = {}
        //Object.entries(textInputs).map( ([key, val]) => {
        Object.values(textInputs).forEach( val => {
            //@ts-ignore
            results[val.id] = val.value;
        });

        try {
            props.handleSubmit(results);
        }catch(err){
            console.log(err);
        }
    }

    const getView = () => {
        //console.log(props)
        
        if(Object.keys(props.data === undefined || props.data === null || props.data).length === 0)
            return <Spinner/>

        let title = (props.title === undefined) ? 'ModelView' : props.title;
        return (
            //@ts-ignore
            <form ref={formDetails} autoComplete="off" onSubmit={handleClick}><fieldset><legend>{title}</legend>
            { 
                Object.entries(props.data).map(([key, val], index) => {
                    let keyName = key.charAt(0).toUpperCase() + key.replace( /([A-Z])/g, " $1" ).slice(1);

                    //@ts-ignore
                    let value:string = val
                    let disabledInput = true
                    let inputType = 'text'
                    let pattern = '.*'

                    switch(key){
                        case 'firstName':
                        case 'lastName':
                        case 'address':
                            disabledInput = false
                            break;
                        case 'phoneNumber':
                            disabledInput = false
                            inputType = 'tel'
                            pattern='[0-9]{10}'
                            break
                    }

                    if(props && props.blank){
                        return ( 
                            //@ts-ignore
                            <span key={index}>
                            <label htmlFor={key}>{keyName}</label>                            
                            <input type={inputType} id={key} name={key} disabled={disabledInput} pattern={pattern} required/>
                            </span>
                        );                        
                    }else{
                        return ( 
                            //@ts-ignore
                            <span key={index}>
                            <label htmlFor={key}>{keyName}</label>                            
                            <input type={inputType} id={key} name={key} defaultValue={value} disabled={disabledInput} pattern={pattern} required/>
                            </span>
                        );
                    }
                }) 
            } 
            <button type='submit'>Submit</button>
            </fieldset></form>
        );        
    }

    return (
        <div className='ModelView'>
            { getView() }
        </div>
    );
}
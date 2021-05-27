import './EmployeeCreateForm.css';
import {MouseEvent, useRef} from 'react';
import Spinner from '../Spinner'

/**
 * 
 * @param props 
 * @returns 
 */
export default function EmployeeCreateForm(props: any) {

    const formDetails = useRef(null);

    const handleClick = (evt: MouseEvent) => {
        evt.preventDefault();
        //@ts-ignore
        const textInputs = [...formDetails.current].filter(element => (element.type === "text" || element.type === "email" || element.type === "tel" ) && element.value.length > 0);
        let results = {}
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
        if(Object.keys(props.data === undefined || props.data === null || props.data).length === 0)
            return <Spinner/>

        let title = (props.title === undefined) ? 'ModelView' : props.title;
        return (
            //@ts-ignore
            <form ref={formDetails} onSubmit={handleClick} autoComplete="off"><fieldset><legend>{title}</legend>
            { 
                Object.entries(props.data).map(([key, val], index) => {
                    let keyName = key.charAt(0).toUpperCase() + key.replace( /([A-Z])/g, " $1" ).slice(1)

                    let inputType = 'text'
                    let pattern = '.*'
                    switch(key){
                        case 'email':
                            inputType = 'email'
                        break;
                        case 'phoneNumber':
                            inputType = 'tel'
                            pattern='[0-9]{10}'
                        break;
                    }
                    
                    return ( 
                        //@ts-ignore
                        <span key={index}>             
                        <input type={inputType} id={key} name={key} defaultValue={''} placeholder={keyName} pattern={pattern} required/>
                        </span>
                    );
                }) 
            } 
            <button type='submit'>Submit</button>
            </fieldset></form>
        );        
    }

    return (
        <div className='EmployeeCreateForm'>
            { getView() }
        </div>
    );
}
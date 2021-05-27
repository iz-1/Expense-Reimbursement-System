import { useEffect, useRef, useState } from "react"
import RequestCreateLine from './RequestCreateLine'
import './RequestCreateForm.css'
import MockDao from "../../MockDao/MockDao"

/**
 * 
 * @param {*} props 
 * @returns 
 */
export default function RequestCreateForm(props) {
    const dateInput = useRef()
    const textInput = useRef()
    const amountInput = useRef()
    const formRef = useRef()

    const [listItems, setItems] = useState({})
    const [total, setTotal] = useState(0)
    const [startDate, setStartDate] = useState(null)
    const [endDate, setEndDate] = useState(null)

    const [listSize, setSize] = useState(0)

    useEffect(()=>{

        if(props && props.data && props.data.length > 0) {
            let expensesList = props.data[0].expenses
            let readExpense = []
            expensesList.forEach(e => {
                console.log(e)

                let entry = {
                    expense: e.expense,
                    amount: parseInt(e.amount),
                    date: e.date
                }

                readExpense.push(entry)
            })

            // this creates a link of states
            setItems( {listItems: readExpense} )
            setTotal(props.data[0].total)
            setSize(expensesList.length)      
        }

    },[props, listItems])



    const handleSubmit = (event) => {
        event.preventDefault()
        let values = getValues()
        if(values === undefined || values.length === 0)
        {
            alert('Please fill out expenses')            
            return;
        }
        let object = {
            expenses: values,
            total: total,
            startDate: startDate,
            endDate: endDate,
            requesterId: MockDao.getCurrentUserId()
        }
        props.handleSubmit(object)
    }

    const addItem = (entry) => {
        if(formRef.current.checkValidity() === false) {
            formRef.current.reportValidity()
            return
        }
            

        if(entry.expense.length === 0)
        {
            alert('Please enter an expense.')
            return;
        }

        if(new Date(entry.date).getTime() > new Date().getTime())
        {
            alert('Please select a valid date.')
            return;
        }        

        // set date range
        if(startDate === null) {
            setStartDate(entry.date)
        } else if(new Date(entry.date).getTime() < new Date(startDate).getTime()) {
            setStartDate(entry.date)
        }

        if(endDate === null) {
            setEndDate(entry.date)
        } else if (new Date(entry.date).getTime() > new Date(endDate).getTime()) {
            setEndDate(entry.date)
        }     

        if(isNaN(entry.amount))
        {
            alert('Please enter an amount.')
            return;
        }        

        entry.amount = parseFloat(entry.amount).toFixed(2) * 100

        if(entry.amount < 1)
        {
            alert('Please enter an amount.')
            return;
        }        

        setTotal(total + entry.amount)

        // this creates a link of states
        setItems( {listItems: {...listItems, ...entry}} )
        setSize(listSize + 1)
    }

    const handleAddClick = (event) => {
        event.preventDefault()
        
        let entry = {
            expense: textInput.current.value,
            amount: amountInput.current.value,
            date: dateInput.current.value || dateInput.current.defaultValue
        }

        addItem(entry)
    }

    const getValues = () => {
        // reverse the listItems linked list
        const stack = [];
        const items = [];                
        let listNode = listItems.listItems
        while(listNode !== undefined) {
            stack.push( {date: listNode.date, expense: listNode.expense, amount: listNode.amount} )
            listNode = listNode.listItems
        }
        while(stack.length > 0)
            items.push(stack.pop())

        return items;
    }

    const removeEntry = (spanObj) => {
    //     let index = spanObj.target.id
    //     let indexInvert = listSize - index - 1

    //     // build a new new list
    //     let listNode = listItems
    //     let newList = null
    //     while(listNode !== undefined) {
    //         if(listNode.id != indexInvert)
    //         {
    //         if(newList === null)
    //             newList = listNode
    //         else
    //             newList.listItems = listNode
    //         }   

    //         listNode = listNode.listItems
    //     }

    //     console.log(listItems)
    //     console.log(newList)
    //     setItems(newList)
    }

    const getItems = () => {
        let items = getValues()
        
        let elements = []

        items.forEach( (e, i) => {
            let formatDate = new Date(e.date).toLocaleDateString('en-US', {timeZone: 'UTC'})
            let formatAmt = e.amount * 0.01
            elements.push(<RequestCreateLine id={i+1} date={formatDate} expense={e.expense} amount={formatAmt} handleSubmit={removeEntry}/>)
        })

        let formatTotal = (total * 0.01).toFixed(2)
        let finalEntryKey = items.length+2
        return (
            <table><thead><tr key={0}><th>Date</th><th>Expense</th><th>Amount</th></tr></thead><tbody>
            {elements}
            <tr key={finalEntryKey}><td></td><td></td><td className='enMoney'>{formatTotal}</td></tr>
            </tbody></table>
        )
    }

    return(
        <div className="RequestCreateForm">
            <form ref={formRef} onSubmit={handleSubmit} autoComplete="off"><fieldset>
                <ul>
                <li key={0}><input ref={dateInput} type="date" id="dateInput" defaultValue={new Date().toISOString().split('T')[0]}/></li>
                <li key={1}><input ref={textInput} type="text" id="textInput" placeholder='Expense...' required/></li>
                <li key={2}><input ref={amountInput} type="number" id="amountInput" min="0.01" step="0.01" placeholder='Cost...' required/></li>
                </ul>
                <button className='btnAdd' onClick={handleAddClick}>Add</button>
            </fieldset>

            { getItems() }

            <button type='submit' className='btnSubmit'>Submit</button>
            </form>
        </div>
    )
}
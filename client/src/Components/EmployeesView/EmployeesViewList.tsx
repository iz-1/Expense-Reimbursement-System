import EmployeesViewListEntry from './EmployeesViewListEntry'

/**
 * 
 * @param props 
 * @returns 
 */
export default function EmployeesViewList(props: any) {

    const getItems = () => {
        const items: any = [];

         //@ts-ignore
        var i:number;
        for(i = 0; i<props.data.length; i++) {
            let d = props.data[i]
            items.push(<EmployeesViewListEntry data={d} key={i+1} />)
        }

        return (
            <table><thead>
                <tr key={0}>
                    <th>Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Type</th>
                </tr></thead><tbody>
            {items}
            </tbody></table>      
        )
    }

    return (        
        <div className='EmployeesViewList'>
            {getItems()}
        </div>
    );
}
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import ru.otus.Atm;
import ru.otus.money.Dollars;
import ru.otus.money.Money;
import ru.otus.money.Rubles;
import ru.otus.money.Type;
import ru.otus.withdraw.Rub;
import static org.junit.Assert.assertEquals;

public class DepartmentTest {

    private Department department;

    @Before
    public void setup() {
        final Set<Money> monies = new HashSet<>();
        monies.add(
            new Rubles(
                new TreeSet<Integer>() {{
                    add(5000);
                    add(2000);
                    add(1000);
                    add(500);
                    add(200);
                    add(100);
                }},
                24000
            )
        );
        monies.add(
            new Dollars(
                new TreeSet<Integer>() {{
                    add(500);
                    add(200);
                    add(100);
                    add(50);
                }},
                4000
            )
        );
        final Set<CacheMachine> machines = new HashSet<>();
        machines.add(
            new CacheMachine(
                new Atm(monies)
            )
        );
        machines.add(
            new CacheMachine(
                new Atm(monies)
            )
        );
        this.department = new Department(machines);
    }

    @Test
    public void shouldCountBalance() {
        Map<Type, Integer> result = new HashMap<>();
        result.put(Type.RUR, 48000);
        result.put(Type.USD, 8000);
        assertEquals(result, this.department.balance());
    }

    @Test
    public void shouldWithdraw() {
        Map<Type, Integer> result = new HashMap<>();
        result.put(Type.RUR, 46400);
        result.put(Type.USD, 8000);
        this.department
            .machines()
            .stream()
            .map(CacheMachine::atm)
            .forEach(atm -> atm.withdraw(new Rub(400)));
        assertEquals(result, this.department.balance());
    }

    @Test
    public void shouldReset() {
        Map<Type, Integer> result = new HashMap<>();
        result.put(Type.RUR, 48000);
        result.put(Type.USD, 8000);
        this.department
            .machines()
            .stream()
            .map(CacheMachine::atm)
            .forEach(atm -> atm.withdraw(new Rub(400)));
        this.department.reset();
        assertEquals(result, this.department.balance());
    }
}

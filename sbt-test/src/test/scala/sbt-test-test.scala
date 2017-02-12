import org.scalatest.FunSuite

// Here using FunSuite style - but other possibilities...
// http://www.scalatest.org/user_guide/selecting_a_style

class SetSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

}


// eof

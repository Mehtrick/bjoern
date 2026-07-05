---
name: bjoern-zgr
description: Use when writing, reviewing, or refactoring .zgr specification files for the Bjoern BDD test generator. Covers syntax, parameter usage for statement reuse, and conventions that reduce the number of methods to implement.
---

# Writing Bjoern .zgr Files

## How Bjoern Works

1. `.zgr` files (YAML-based BDD specs) are read by the parser
2. The generator creates **abstract Java test classes** + **interfaces** via JavaPoet
3. A Freemarker template produces AsciiDoc documentation
4. Gradle tasks: `bjoerngen` (code) and `bjoerndoc` (docs)

Generated code lives in `src/gen/` and is **regenerated on every run** — never edit it.

## When to Use Bjoern

Bjoern is ideal for **acceptance-oriented tests** where **business logic** is paramount and the **specification serves as living documentation**.

### Test Type Suitability

| Test Type | Suitability | Recommendation |
|-----------|-------------|----------------|
| **Use Cases / User Journeys** | ★★★★★ | **Ideal** - Primary use case |
| **Acceptance Tests** | ★★★★★ | **Ideal** - Stakeholders understand specs |
| **Business Logic / Domain Rules** | ★★★★★ | **Ideal** for complex domain rules |
| **API Tests** | ★★★★☆ | **Very good** with technical abstraction |
| **Integration Tests** | ★★★★☆ | **Very good** for multi-component workflows |
| **UI Tests** | ★★★☆☆ | Good with Page Object Pattern |
| **Security Tests** | ★★★☆☆ | Only for business security requirements |
| **Unit Tests** | ★★☆☆☆ | Only for complex business logic |
| **Performance Tests** | ★☆☆☆☆ | **Not suitable** |

### Use Cases (Primary Use Case)

Bjoern excels at testing **use cases** - complete user journeys that deliver business value.

**What makes a good use case for Bjoern:**
- Has clear business value
- Involves multiple steps/interactions
- Has observable outcomes
- Can be described in business language
- Stakeholders care about the behavior

**Example: E-Commerce Checkout Use Case**
```yaml
Feature: Customer checkout process

Scenario: Customer completes purchase with credit card
  Given:
    - Customer "alice" has items in cart
    - Cart total is "150" Euro
    - Customer has payment method "Credit Card"
  When:
    - Customer proceeds to checkout
    - Customer enters shipping address "Main St 123, Berlin"
    - Customer confirms payment with "Credit Card"
  Then:
    - Order is created with number "ORD-2024-001"
    - Payment of "150" Euro is processed
    - Confirmation email is sent to "alice@example.com"
    - Cart is empty

Scenario: Checkout fails due to payment declined
  Given:
    - Customer "bob" has items in cart
    - Cart total is "200" Euro
    - Payment method "Credit Card" is declined
  When:
    - Customer proceeds to checkout
    - Customer confirms payment with "Credit Card"
  Then:
    - Order is not created
    - Error message is "Payment declined"
    - Cart still contains items
    - No email is sent
```

**Example: User Registration Use Case**
```yaml
Feature: User registration and onboarding

Scenario: New user registers successfully
  Given:
    - Email "newuser@example.com" is not registered
  When:
    - User submits registration with email "newuser@example.com" and password "SecurePass123"
  Then:
    - User account is created with role "USER"
    - Verification email is sent to "newuser@example.com"
    - User is redirected to verification page

Scenario: Registration fails with existing email
  Given:
    - Email "existing@example.com" is already registered
  When:
    - User submits registration with email "existing@example.com"
  Then:
    - Registration is rejected
    - Error message is "Email already registered"
    - No account is created
```

### When to Use Bjoern

✅ **Use Bjoern when:**
- Business requirements are the focus
- Non-developers should read the specs
- Living documentation is desired
- Business rules are complex and varied
- Cross-component workflows are tested
- Use cases need to be documented and validated

❌ **Don't use Bjoern when:**
- Tests are purely technical (performance, memory)
- Granular unit tests are needed
- No business domain exists
- Team doesn't want/need BDD culture

### Hybrid Approach (Recommended)

```
├── Unit Tests (JUnit/Mockito)
│   └── Technical correctness
├── Bjoern Specs
│   ├── Use Cases / User Journeys
│   ├── Acceptance tests
│   ├── Integration tests
│   └── API tests
└── Specialized Tests
    ├── Performance (Gatling, JMeter)
    └── Security (OWASP ZAP)
```

**Best strategy:** Use Bjoern for the **business level** (What should the system do?), other tools for the **technical level** (How performant/secure is it?).

## .zgr File Structure

```yaml
Feature: Description of the feature
Version: "1.0.0"                                          # optional
Reference: "[TICKET-123](https://example.com/TICKET-123)" # optional
Changelog: "Changed behavior in v2"                       # optional

Background:
  Given:
    - User "alice" exists
    - User is logged in

Scenarios:
  - Scenario: Customer views account balance
    Given:
      - Account balance is "100"
    When:
      - Customer views dashboard
    Then:
      - Displayed balance is "100"

  - Scenario: Customer withdraws money
    Given:
      - Account balance is "100"
    When:
      - Customer withdraws "30"
    Then:
      - Account balance is "70"
      - Transaction log shows withdrawal
```

## The Key Principle: Parameterize for Reuse

**Quoted strings `"..."` become method parameters.** Statements with the same structure (same text around parameters) map to the **same method**, reducing the interface size.

### Bad: Duplicate Methods

```yaml
Scenarios:
  - Scenario: Cola available
    Given:
      - 2 bottles of Cola
      - 0 bottles of Sprite
    When:
      - Buy Cola
    Then:
      - Automat says "ok"

  - Scenario: Sprite available
    Given:
      - 0 bottles of Cola
      - 2 bottles of Sprite
    When:
      - Buy Sprite
    Then:
      - Automat says "ok"
```

This generates **6+ distinct methods** because the text differs:
- `given_2BottlesOfCola()`
- `given_0BottlesOfSprite()`
- `given_0BottlesOfCola()`
- `given_2BottlesOfSprite()`
- `when_BuyCola()`
- `when_BuySprite()`

### Good: Parameterized Statements

```yaml
Scenarios:
  - Scenario: Customer buys Cola from vending machine
    Given:
      - Vending machine contains "2" bottles of Cola
      - Vending machine contains "0" bottles of Sprite
    When:
      - Customer purchases "Cola"
    Then:
      - Vending machine displays "ok"

  - Scenario: Customer buys Sprite from vending machine
    Given:
      - Vending machine contains "0" bottles of Cola
      - Vending machine contains "2" bottles of Sprite
    When:
      - Customer purchases "Sprite"
    Then:
      - Vending machine displays "ok"
```

This generates only **3 methods** (reused across scenarios):
- `given_VendingMachineContainsBottlesOfCola(String param1)`
- `given_VendingMachineContainsBottlesOfSprite(String param1)`
- `when_CustomerPurchases(String param1)`

Notice the improvements:
- **Business language**: "Customer purchases" instead of "Buy"
- **Declarative**: "Vending machine contains" (state) instead of hardcoded numbers
- **Consistent**: Same phrasing across scenarios

## Parameter Rules

1. **Parameters are extracted from double-quoted strings** `"value"`
2. The text around parameters forms the method name (camelCased)
3. Same structure = same method, regardless of parameter values
4. Parameters are always `String` type in the generated interface
5. Multiple parameters per statement are supported (each `"..."` becomes a separate argument)

### Example: Multiple Parameters

```yaml
Given:
  - User "alice" has role "admin"
```

Generates: `given_UserHasRole(String param1, String param2)`

Called as: `given_UserHasRole("alice", "admin")`

## Statement Character Rules

**Outside parameters (quoted strings):** Only letters (a-z, A-Z), numbers (0-9), and spaces.

**Inside parameters:** Any characters are allowed.

### Allowed Characters (outside quotes)

| Character | Allowed? | Example |
|-----------|----------|---------|
| Letters | Yes | `User`, `Automat`, `Flasche` |
| Numbers | Yes | `2`, `100` (but prefer parameterizing) |
| Spaces | Yes | `bottles of Cola` |
| Special chars | **No** | `@`, `#`, `$`, `%`, `&`, `*`, `!`, `?`, `/`, `\`, `|`, `~`, `` ` `` |
| Punctuation | **No** | `.`, `,`, `:`, `;`, `-`, `_`, `(`, `)`, `[`, `]`, `{`, `}`, `"`, `'` |

### Bad: Special Characters in Statement Text

```yaml
Given:
  - User's email is test@example.com  # ❌ apostrophe, @ symbol
  - Path: /home/user/file.txt         # ❌ colon, slashes
  - Price is $100.00!                 # ❌ dollar sign, period, exclamation
  - Items (max 10)                    # ❌ parentheses
```

### Good: Move Special Characters into Parameters

```yaml
Given:
  - User email is "test@example.com"           # ✓ special chars in parameter
  - File path is "/home/user/file.txt"         # ✓ special chars in parameter
  - Product price is "100.00" in currency "USD" # ✓ numbers/symbols parameterized
  - Cart items limited to maximum "10"         # ✓ number parameterized
```

## Consistent Language

**Use the language that is established in the project.** Check existing `.zgr` files and use the same language consistently throughout all specs.

### Check Existing Specs First

Before writing new specs:
1. Look at existing `.zgr` files in the project
2. Identify the language used (German, English, etc.)
3. Use that language for all new statements

### Example: German Project

If existing specs use German:
```yaml
Feature: Test eines Getraenkeautomaten
Background:
  Given:
    - Ein Automat
    - Ein KassenSystem
Scenarios:
  - Scenario: Getraenk nicht vorhanden
    Given:
      - Mit "2" Flaschen Cola
    When:
      - Kaufe "1" Sprite
    Then:
      - Automat sagt "alle"
      - Es existieren "2" Cola im Automaten
```

**Don't mix languages:**
```yaml
# ❌ Bad: Mixed German and English
Given:
  - Ein Automat
  - A cash system           # ❌ English in German spec
  - The user is logged in   # ❌ English in German spec
```

### Example: English Project

If existing specs use English:
```yaml
Feature: Test Foo
Background:
  Given:
    - A Foo exists
    - A Bar is configured
Scenarios:
  - Scenario: Foo is not happy
    Given:
      - there are "2" bottles of wine
    When:
      - Foo wants to drink "1" bottle of beer
    Then:
      - Foo says "why is my beer empty"
```

### Language Consistency Rules

- **Feature descriptions**: Same language as project
- **Scenario names**: Same language as project
- **Given/When/Then statements**: Same language as project
- **Parameter values**: Can be in any language (they're data)

**Consistent terminology within the same language:**
```yaml
# ❌ Bad: Inconsistent terms
Scenario: User login
  Given:
    - Ein Benutzer existiert

Scenario: Password reset
  Given:
    - Ein Kunde ist registriert    # ❌ "Kunde" vs "Benutzer"

# Good: Consistent terms
Scenario: User login
  Given:
    - Benutzer "alice" existiert

Scenario: Password reset
  Given:
    - Benutzer "bob" existiert     # ✓ Same term "Benutzer"
```

## BDD Best Practices: Sprechende Statements

### Use Business Language, Not Technical Terms

Statements should read like a conversation with a domain expert, not a developer.

**Bad: Technical Implementation**
```yaml
Given:
  - Database contains user record with id "123"
  - API endpoint is configured with timeout "5000"
  - Session token is stored in cookie
```

**Good: Business Behavior**
```yaml
Given:
  - User "123" exists in the system
  - Request timeout is set to "5000" milliseconds
  - User is logged in
```

### Declarative, Not Imperative

Describe **what** happens, not **how** it happens.

**Bad: Imperative (How)**
```yaml
When:
  - Click the submit button
  - Navigate to the dashboard page
  - Enter "admin" in the username field
```

**Good: Declarative (What)**
```yaml
When:
  - User submits the form
  - User views the dashboard
  - User logs in with username "admin"
```

### One Statement, One Responsibility

Each statement should express exactly one fact or action.

**Bad: Multiple Concepts**
```yaml
Given:
  - User "admin" is logged in and has admin role
  - Cart contains "3" items with total price "150"
```

**Good: Single Concept per Statement**
```yaml
Given:
  - User "admin" is logged in
  - User "admin" has role "admin"
  - Cart contains "3" items
  - Cart total is "150"
```

### Use Concrete Examples

Prefer specific values over abstract descriptions.

**Bad: Abstract**
```yaml
Given:
  - Some users exist
  - A product is available
  - The order is pending
```

**Good: Concrete**
```yaml
Given:
  - "3" users exist in the system
  - Product "Laptop" is available
  - Order "ORD-123" is pending
```

### Consistent Naming Across Scenarios

Use the same terminology everywhere.

**Bad: Inconsistent Terms**
```yaml
Scenario: User login
  Given:
    - A customer exists

Scenario: Password reset
  Given:
    - An account is registered

Scenario: Profile update
  Given:
    - A member is authenticated
```

**Good: Consistent Domain Language**
```yaml
Scenario: User login
  Given:
    - User "alice" exists

Scenario: Password reset
  Given:
    - User "bob" exists

Scenario: Profile update
  Given:
    - User "charlie" exists
```

### Scenario Names Should Describe the Business Case

**Bad: Vague or Technical**
```yaml
  - Scenario: Test case 1
  - Scenario: Error handling
  - Scenario: Happy path
```

**Good: Descriptive Business Scenarios**
```yaml
  - Scenario: User successfully logs in with valid credentials
  - Scenario: Login fails after three incorrect attempts
  - Scenario: Account locked after maximum failed attempts
```

### Given-When-Then Semantics

- **Given**: Preconditions and context (past tense or state)
- **When**: Actions or events (present tense, active voice)
- **Then**: Observable outcomes (present tense, verifiable)

**Example:**
```yaml
Given:
  - Account balance is "100"          # state (past)
When:
  - User withdraws "30"               # action (present)
Then:
  - Account balance is "70"           # outcome (verifiable)
  - Transaction log shows withdrawal  # outcome (verifiable)
```

## Writing Effective Then-Statements

Then-statements define **what must be verified** after the action. They should be specific, testable, and cover all relevant aspects of the expected outcome.

### Types of Assertions

#### 1. State Verification (Zustandsprüfung)

Verify the state of entities, collections, or system state after the action.

```yaml
Then:
  - Account balance is "70"                    # entity state
  - Shopping cart contains "3" items           # collection size
  - Order status is "COMPLETED"                # enum/state value
  - User "alice" has role "admin"              # relationship
  - Product "Laptop" is no longer available    # negative state
```

**Implementation hint:** These typically map to repository queries or entity getters.

#### 2. Return Value / Response Verification

Verify what the system returns or displays.

```yaml
Then:
  - Response status is "200"
  - Response contains message "Success"
  - Displayed balance is "70"
  - Error message is "Insufficient funds"
  - Generated invoice number is "INV-2024-001"
```

**Implementation hint:** These check HTTP responses, UI elements, or return values.

#### 3. Side Effect Verification

Verify that side effects occurred (events, logs, notifications).

```yaml
Then:
  - Email was sent to "alice@example.com"
  - Audit log contains entry "User logged in"
  - Payment transaction was created
  - Notification was triggered for user "bob"
  - Cache was invalidated for key "product-list"
```

**Implementation hint:** These often require mocking frameworks or event listeners.

#### 4. Exception / Error Verification

Verify that operations fail correctly.

```yaml
Then:
  - Operation fails with error "Insufficient funds"
  - Validation error occurs for field "email"
  - User receives error code "404"
  - Transaction is rolled back
```

**Implementation hint:** Use AssertJ's `assertThatThrownBy()` or similar.

### Multiple Assertions per Scenario

A scenario can have multiple Then-statements to verify different aspects:

```yaml
Scenario: Successful money transfer
  Given:
    - Account "A" has balance "100"
    - Account "B" has balance "50"
  When:
    - Transfer "30" from account "A" to account "B"
  Then:
    - Account "A" has balance "70"
    - Account "B" has balance "80"
    - Transaction log shows transfer of "30"
    - No errors occurred
```

### Transaction Safety (Transaktionssicherheit)

For systems requiring transactional integrity, verify consistency guarantees:

```yaml
Scenario: Transfer fails due to insufficient funds
  Given:
    - Account "A" has balance "50"
    - Account "B" has balance "100"
  When:
    - Transfer "80" from account "A" to account "B"
  Then:
    - Operation fails with error "Insufficient funds"
    - Account "A" has balance "50"      # unchanged (rollback)
    - Account "B" has balance "100"     # unchanged (rollback)
    - No transaction was recorded
```

**Spring @Transactional implementation:**
```java
@Transactional  // Ensures rollback on exception
public void transfer(String fromAccount, String toAccount, BigDecimal amount) {
    Account from = accountRepository.findById(fromAccount)
        .orElseThrow(() -> new InsufficientFundsException());
    
    if (from.getBalance().compareTo(amount) < 0) {
        throw new InsufficientFundsException("Insufficient funds");
    }
    
    from.debit(amount);
    Account to = accountRepository.findById(toAccount).get();
    to.credit(amount);
    
    accountRepository.save(from);
    accountRepository.save(to);
    transactionLog.record(fromAccount, toAccount, amount);
}
```

**Key transaction properties to verify:**
- **Atomicity**: All operations succeed or all fail (no partial updates)
- **Consistency**: System state is valid before and after transaction
- **Isolation**: Concurrent transactions don't interfere
- **Durability**: Committed changes persist

### Common Then-Patterns from Real Specs

#### Pattern 1: Quantity Verification
```yaml
# Bad: Vague
Then:
  - Items were added

# Good: Specific count
Then:
  - Cart contains "3" items
  - Inventory shows "7" remaining units
```

#### Pattern 2: State Transition Verification
```yaml
# Bad: Only checks final state
Then:
  - Order is completed

# Good: Checks state transition
Then:
  - Order status is "COMPLETED"
  - Order completion timestamp is set
  - Payment status is "PAID"
```

#### Pattern 3: Negative Verification
```yaml
# Bad: Only positive assertions
Then:
  - User exists

# Good: Include negative checks
Then:
  - User "alice" exists
  - User "alice" has no pending orders
  - No error was logged
```

#### Pattern 4: Message/Response Verification
```yaml
# Bad: Generic
Then:
  - User receives message

# Good: Specific message content
Then:
  - User receives message "Welcome, alice!"
  - Message type is "SUCCESS"
  - Message timestamp is after transaction time
```

### Then-Statement Checklist

When writing Then-statements, ensure:

- [ ] **Verifiable**: Can be checked programmatically (no subjective statements)
- [ ] **Specific**: Exact values, not "some" or "many"
- [ ] **Complete**: Cover all relevant aspects (state, response, side effects)
- [ ] **Independent**: Each assertion checks one thing
- [ ] **Ordered logically**: Most important assertions first
- [ ] **Transaction-safe**: Verify rollback behavior for failure scenarios
- [ ] **Parameterized**: Use `"quotes"` for varying values to enable reuse

### Anti-Patterns to Avoid

**❌ Subjective assertions:**
```yaml
Then:
  - System is fast                    # How fast?
  - User is happy                     # How to measure?
  - Data looks correct                # What is "correct"?
```

**✅ Objective assertions:**
```yaml
Then:
  - Response time is under "200" milliseconds
  - User receives confirmation message "Success"
  - Database contains "5" records with status "VALID"
```

**❌ Implementation details:**
```yaml
Then:
  - Method save() was called          # Too technical
  - SQL INSERT was executed           # Implementation detail
```

**✅ Business outcomes:**
```yaml
Then:
  - User "alice" was persisted        # Business outcome
  - Order "ORD-123" exists            # Business outcome
```

### Avoid Redundancy

Don't repeat information that's already clear from context.

**Bad: Redundant**
```yaml
Background:
  Given:
    - System is initialized
    - Database connection is established
    - Application is running

Scenarios:
  - Scenario: User login
    Given:
      - System is ready
      - User "alice" exists
```

**Good: Essential Context Only**
```yaml
Background:
  Given:
    - User "alice" exists

Scenarios:
  - Scenario: User login
    Given:
      - User "alice" is logged in
```

## Method Name Generation

The generator:
1. Strips quoted parameters from the statement
2. Removes invalid chars: `( ) . , - _ : = + ' < > ^`
3. Converts to CamelCase
4. Prefixes with keyword: `given_`, `when_`, `then_`

**Important:** Even though the generator strips some special characters, you should **avoid them entirely** in statement text to keep specs readable and prevent unexpected method name collisions.

| Statement | Generated Method |
|-----------|------------------|
| `User exists` | `given_UserExists()` |
| `Vending machine contains "2" bottles of Cola` | `given_VendingMachineContainsBottlesOfCola(String param1)` |
| `Customer purchases "Cola"` | `when_CustomerPurchases(String param1)` |
| `Account balance is "100"` | `given_AccountBalanceIs(String param1)` |
| `Order "ORD-123" is pending` | `given_OrderIsPending(String param1)` |

## Writing Guidelines

### DO:
- **Parameterize variable data** (numbers, names, states) with `"quotes"`
- **Keep statement text stable** across scenarios — only change parameter values
- **Use only letters, numbers, and spaces** in statement text (outside quotes)
- **Move special characters into parameters** (email, paths, prices, etc.)
- **Use Background** for setup common to all scenarios
- **Reuse Given/When/Then phrasing** exactly to maximize method reuse
- **Use Version/Reference** fields for traceability
- **Use business language** — domain terms, not technical implementation
- **Be declarative** — describe what happens, not how
- **Use concrete values** — specific examples, not abstract descriptions
- **Be consistent** — same term for same concept everywhere
- **Write descriptive scenario names** — business cases, not "test 1"

### DON'T:
- Don't hardcode values that vary between scenarios
- Don't use special characters in statement text (`@`, `#`, `$`, `.`, `,`, `-`, etc.)
- Don't rephrase the same concept differently (e.g., "Buy Cola" vs "Purchase Cola")
- Don't put implementation details in statements (no "click button", "call API")
- Don't edit generated files in `src/gen/`
- Don't use technical jargon (no "database record", "API endpoint")
- Don't write imperative instructions (no "click", "navigate", "enter")
- Don't combine multiple concepts in one statement
- Don't use vague scenario names ("test case 1", "error handling")

## Formatting .zgr Files

After modifying a `.zgr` file, ensure proper YAML formatting:

### Required Format

```yaml
Feature: Customer manages shopping cart
Version: "1.0.0"
Reference: "[CART-123](https://example.com/CART-123)"

Background:
  Given:
    - Customer "alice" is logged in
    - Product catalog is loaded

Scenarios:
  - Scenario: Customer adds product to cart
    Given:
      - Cart is empty
    When:
      - Customer adds product "Laptop" to cart
    Then:
      - Cart contains "1" item
      - Cart total is "999"

  - Scenario: Customer removes product from cart
    Given:
      - Cart contains product "Laptop"
    When:
      - Customer removes product "Laptop" from cart
    Then:
      - Cart is empty
      - Cart total is "0"
```

### Formatting Rules

1. **Indentation:** 2 spaces per level, no tabs
2. **Lists:** Use `- ` (dash + space) for list items
3. **Spacing:** Blank line between `Feature` metadata and `Background`, between `Background` and `Scenarios`, and between scenarios
4. **Quotes:** Always use double quotes `"..."` for parameters
5. **Colons:** Followed by space (`Given: `, not `Given:`)
6. **Alignment:** Statements at same level should be vertically aligned

## Validation Workflow

**After every `.zgr` file modification, always run:**

```bash
./gradlew bjoerngen
```

### What to Check in Console Output

1. **No exceptions:** Look for `BjoernMissingPropertyException`, `BjoernGeneratorException`, or Jackson mapping errors
2. **Generation confirmation:** Should see `Generate Feature: <FeatureName>` for each processed file
3. **No YAML parse errors:** Check for `com.fasterxml.jackson.dataformat.yaml` errors
4. **No validation errors:** The parser validates spec structure

### Common Errors

| Error | Cause | Fix |
|-------|-------|-----|
| `BjoernMissingPropertyException` | Missing required config (folder, pckg, gendir) | Check `build.gradle` bjoern block |
| `Jackson mapping exception` | Invalid YAML syntax | Fix indentation or quotes in `.zgr` |
| `BjoernGeneratorException` | Invalid spec structure | Ensure Feature/Scenarios/Given/When/Then structure |

### Success Indicators

```
Generate Feature: FeatureName
> Task :bjoerngen
BUILD SUCCESSFUL
```

## Checklist for .zgr Files

### Suitability Check (First!)
- [ ] Test type is suitable for Bjoern (Use Cases, Acceptance, Business Logic, Integration, API)
- [ ] Test describes business behavior, not technical implementation
- [ ] Test has clear business value and observable outcomes
- [ ] Stakeholders can understand the scenarios
- [ ] NOT a pure unit test, performance test, or technical test

### Syntax & Formatting
- [ ] All varying values are quoted: `"value"`
- [ ] Statement text contains only letters, numbers, and spaces
- [ ] Special characters (email, paths, etc.) are inside parameters
- [ ] YAML formatting is correct (2-space indent, proper spacing)
- [ ] Optional: Version, Reference, Changelog fields used where helpful

### Language & Terminology
- [ ] Language matches existing project specs (check other .zgr files first)
- [ ] Consistent terminology throughout all scenarios
- [ ] No mixing of languages within a spec

### BDD Quality
- [ ] Statements use business language (no technical terms)
- [ ] Statements are declarative (what, not how)
- [ ] Each statement expresses one concept
- [ ] Concrete values used instead of abstract descriptions
- [ ] Scenario names describe business cases clearly
- [ ] Given = state, When = action, Then = verifiable outcome

### Then-Statement Quality
- [ ] Assertions are specific and verifiable (no subjective statements)
- [ ] Multiple assertions cover all relevant aspects (state, response, side effects)
- [ ] Transaction safety verified for failure scenarios (rollback behavior)
- [ ] Negative checks included where appropriate (what should NOT happen)

### Reuse & Maintenance
- [ ] Same concept uses identical wording across scenarios
- [ ] Background contains shared setup
- [ ] Statements are parameterized for maximum reuse

### Validation
- [ ] **Ran `./gradlew bjoerngen` and verified successful generation**

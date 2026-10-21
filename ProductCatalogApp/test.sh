#!/bin/bash
BASE="http://localhost:8080/products"

echo ""
echo "╔══════════════════════════════════════════════════╗"
echo "║       Product Catalog API — curl Tests           ║"
echo "╚══════════════════════════════════════════════════╝"

echo ""
echo "── 1. GET /products (all products) ─────────────────"
curl -s "$BASE" | python3 -m json.tool

echo ""
echo "── 2. GET /products/1 (single product) ─────────────"
curl -s "$BASE/1" | python3 -m json.tool

echo ""
echo "── 3. POST /products (create Monitor) ──────────────"
curl -s -X POST "$BASE" \
  -H "Content-Type: application/json" \
  -d '{"id":3,"name":"Monitor","price":8500.0}' | python3 -m json.tool

echo ""
echo "── 4. PUT /products/1 (update Keyboard) ────────────"
curl -s -X PUT "$BASE/1" \
  -H "Content-Type: application/json" \
  -d '{"name":"Gaming Keyboard","price":2500.0}' | python3 -m json.tool

echo ""
echo "── 5. DELETE /products/2 (delete Mouse) ────────────"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE "$BASE/2")
echo "   HTTP Status: $STATUS (204 = success)"

echo ""
echo "── 6. GET /products (final state) ──────────────────"
curl -s "$BASE" | python3 -m json.tool

echo ""
echo "╔══════════════════════════════════════════════════╗"
echo "║              All tests completed ✅              ║"
echo "╚══════════════════════════════════════════════════╝"
echo ""
